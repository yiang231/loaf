package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.PropertyValue;
import com.xjdl.framework.beans.factory.Aware;
import com.xjdl.framework.beans.factory.BeanClassLoaderAware;
import com.xjdl.framework.beans.factory.BeanCreationException;
import com.xjdl.framework.beans.factory.BeanFactory;
import com.xjdl.framework.beans.factory.BeanFactoryAware;
import com.xjdl.framework.beans.factory.BeanNameAware;
import com.xjdl.framework.beans.factory.InitializingBean;
import com.xjdl.framework.beans.factory.config.AutowireCapableBeanFactory;
import com.xjdl.framework.beans.factory.config.BeanDefinition;
import com.xjdl.framework.beans.factory.config.BeanPostProcessor;
import com.xjdl.framework.beans.factory.config.BeanReference;
import com.xjdl.framework.core.NativeDetector;
import com.xjdl.framework.util.BeanUtils;
import com.xjdl.framework.util.ReflectionUtils;
import com.xjdl.framework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

@Slf4j
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
	private InstantiationStrategy instantiationStrategy;

	public AbstractAutowireCapableBeanFactory(BeanFactory parentBeanFactory) {
		this();
		setParentBeanFactory(parentBeanFactory);
	}

	public AbstractAutowireCapableBeanFactory() {
		super();
		if (NativeDetector.inNativeImage()) {
			this.instantiationStrategy = new SimpleInstantiationStrategy();
		} else {
			this.instantiationStrategy = new CglibSubclassingInstantiationStrategy();
		}
	}

	@Override
	protected Object creatBean(String beanName, BeanDefinition beanDefinition) {
		Object beanInstance = doCreateBean(beanName, beanDefinition);
		if (log.isTraceEnabled()) {
			log.trace("Finished creating instance of bean '" + beanName + "'");
		}
		return beanInstance;
	}

	/**
	 * BeanWrapper -> bean -> exposedObject
	 */
	private Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
		Object bean = createBeanInstance(beanDefinition, beanName);
		Object exposedObject = bean;
		try {
			populateBean(beanName, beanDefinition, exposedObject);
			exposedObject = initializeBean(beanName, exposedObject, beanDefinition);
		} catch (Exception e) {
			throw new BeanCreationException("Instantiation of bean named '" + beanName + "' failed", e);
		}
		try {
			registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
		} catch (BeanDefinitionValidationException ex) {
			throw new BeanCreationException("Invalid destruction signature " + beanName, ex);
		}
		super.registerSingleton(beanName, exposedObject);
		return exposedObject;
	}

	/**
	 * @param instanceWrapper 包装对象
	 */
	private void populateBean(String name, BeanDefinition beanDefinition, Object instanceWrapper) {
		applyPropertyValues(name, beanDefinition, instanceWrapper);
	}

	/**
	 * @param bean 真实对象
	 */
	protected void invokeInitMethods(String beanName, Object bean, BeanDefinition mbd) throws Throwable {
		boolean isInitializingBean = (bean instanceof InitializingBean);
		if (isInitializingBean && mbd != null) {
			if (log.isTraceEnabled()) {
				log.trace("Invoking afterPropertiesSet() on bean with name '" + beanName + "'");
			}
			if (System.getSecurityManager() != null) {
				try {
					AccessController.doPrivileged((PrivilegedExceptionAction<Object>) () -> {
						((InitializingBean) bean).afterPropertiesSet();
						return null;
					}, getAccessControlContext());
				} catch (PrivilegedActionException pae) {
					throw pae.getException();
				}
			} else {
				((InitializingBean) bean).afterPropertiesSet();
			}
		}
		if (mbd != null) {
			String initMethodName = mbd.getInitMethodName();
			if (StringUtils.hasLength(initMethodName) &&
					!(isInitializingBean && "afterPropertiesSet".equals(initMethodName))) {
				invokeCustomInitMethod(beanName, bean, mbd);
			}
		}
	}

	protected void invokeCustomInitMethod(String beanName, Object bean, BeanDefinition mbd) throws Throwable {
		String initMethodName = mbd.getInitMethodName();
		Method initMethod = BeanUtils.findMethod(bean.getClass(), initMethodName);
		if (log.isTraceEnabled()) {
			log.trace("Invoking init method  '" + initMethodName + "' on bean with name '" + beanName + "'");
		}
		Method methodToInvoke = initMethod;
		if (System.getSecurityManager() != null) {
			AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
				ReflectionUtils.makeAccessible(methodToInvoke);
				return null;
			});
			try {
				AccessController.doPrivileged((PrivilegedExceptionAction<Object>)
						() -> methodToInvoke.invoke(bean), getAccessControlContext());
			} catch (PrivilegedActionException pae) {
				InvocationTargetException ex = (InvocationTargetException) pae.getException();
				throw ex.getTargetException();
			}
		} else {
			try {
				ReflectionUtils.makeAccessible(methodToInvoke);
				methodToInvoke.invoke(bean);
			} catch (InvocationTargetException ex) {
				throw ex.getTargetException();
			}
		}
	}

	/**
	 * 普通属性的注入
	 */
	private void applyPropertyValues(String name, BeanDefinition beanDefinition, Object bean) {
		try {
			for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()) {
				Object value = propertyValue.getValue();
				if (value instanceof BeanReference) {
					BeanReference beanReference = (BeanReference) value;
					value = getBean(beanReference.getBeanName());
				}
				try {
					// setter 注入
					Method declaredMethod = bean.getClass().getDeclaredMethod(
							// set + 首字母大写 + 后续字母
							"set" + propertyValue.getName().substring(0, 1).toUpperCase() +
									propertyValue.getName().substring(1), value.getClass());
					declaredMethod.setAccessible(true);
					declaredMethod.invoke(bean, value);
				} catch (NoSuchMethodException e) {
					// Field 注入
					Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
					declaredField.setAccessible(true);
					declaredField.set(bean, value);
				}
			}
		} catch (Exception e) {
			throw new BeanCreationException("Error setting property values for bean: " + name, e);
		}
	}

	@Override
	protected void clearSingletonCache() {
		synchronized (getSingletonMutex()) {
			super.clearSingletonCache();
		}
	}

	private Object createBeanInstance(BeanDefinition beanDefinition, String name) {
		return instantiateBean(name, beanDefinition);
	}

	protected Object instantiateBean(String beanName, BeanDefinition mbd) {
		try {
			Object beanInstance;
			if (System.getSecurityManager() != null) {
				beanInstance = AccessController.doPrivileged(
						(PrivilegedAction<Object>) () -> getInstantiationStrategy().instantiate(mbd, beanName, this),
						getAccessControlContext());
			} else {
				beanInstance = getInstantiationStrategy().instantiate(mbd, beanName, this);
			}
			return beanInstance;
		} catch (Throwable ex) {
			throw new BeanCreationException("Instantiation of bean named " + beanName + " failed", ex);
		}
	}

	public InstantiationStrategy getInstantiationStrategy() {
		return this.instantiationStrategy;
	}

	@Override
	public Object initializeBean(Object existingBean, String beanName) throws BeansException {
		return initializeBean(beanName, existingBean, null);
	}

	protected Object initializeBean(String beanName, Object bean, BeanDefinition mbd) {
		if (System.getSecurityManager() != null) {
			AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
				invokeAwareMethods(beanName, bean);
				return null;
			}, getAccessControlContext());
		} else {
			invokeAwareMethods(beanName, bean);
		}
		applyBeanPostProcessorsBeforeInitialization(bean, beanName);
		try {
			invokeInitMethods(beanName, bean, mbd);
		} catch (Throwable ex) {
			throw new BeanCreationException("Invocation of init method failed", ex);
		}
		applyBeanPostProcessorsAfterInitialization(bean, beanName);
		return bean;
	}

	private void invokeAwareMethods(String beanName, Object bean) {
		if (bean instanceof Aware) {
			if (bean instanceof BeanNameAware) {
				((BeanNameAware) bean).setBeanName(beanName);
			}
			if (bean instanceof BeanClassLoaderAware) {
				ClassLoader bcl = getBeanClassLoader();
				if (bcl != null) {
					((BeanClassLoaderAware) bean).setBeanClassLoader(bcl);
				}
			}
			if (bean instanceof BeanFactoryAware) {
				((BeanFactoryAware) bean).setBeanFactory(AbstractAutowireCapableBeanFactory.this);
			}
		}
	}

	@Override
	public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
		Object result = existingBean;
		for (BeanPostProcessor processor : getBeanPostProcessors()) {
			Object current = processor.postProcessBeforeInitialization(result, beanName);
			if (current == null) {
				return result;
			}
			result = current;
		}
		return result;
	}

	@Override
	public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
		Object result = existingBean;
		for (BeanPostProcessor processor : getBeanPostProcessors()) {
			Object current = processor.postProcessAfterInitialization(result, beanName);
			if (current == null) {
				return result;
			}
			result = current;
		}
		return result;
	}
}
