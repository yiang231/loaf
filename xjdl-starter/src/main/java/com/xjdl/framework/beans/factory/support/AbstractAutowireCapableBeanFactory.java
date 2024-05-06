package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.PropertyValue;
import com.xjdl.framework.beans.PropertyValues;
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
import com.xjdl.framework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.xjdl.framework.core.NativeDetector;
import com.xjdl.framework.util.BeanUtils;
import com.xjdl.framework.util.ReflectionUtils;
import com.xjdl.framework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.util.Locale.ENGLISH;

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
		if (log.isTraceEnabled()) {
			log.trace("Creating instance of bean '{}'", beanName);
		}
		try {
			Object beanInstance = doCreateBean(beanName, beanDefinition);
			if (log.isTraceEnabled()) {
				log.trace("Finished creating instance of bean '{}'", beanName);
			}
			return beanInstance;
		} catch (BeanCreationException | IllegalStateException ex) {
			throw ex;
		} catch (Throwable ex) {
			throw new BeanCreationException("Unexpected exception during bean '" + beanName + "' creation", ex);
		}
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
		if (beanDefinition.isSingleton()) {
			super.registerSingleton(beanName, exposedObject);
		}
		return exposedObject;
	}

	/**
	 * @param instanceWrapper 包装对象
	 */
	private void populateBean(String name, BeanDefinition beanDefinition, Object instanceWrapper) {
		PropertyValues pvs = (beanDefinition.hasPropertyValues() ? beanDefinition.getPropertyValues() : null);
		boolean hasInstAwareBpps = hasInstantiationAwareBeanPostProcessors();
		if (hasInstAwareBpps) {
			for (BeanPostProcessor bp : getBeanPostProcessors()) {
				if (InstantiationAwareBeanPostProcessor.class.isAssignableFrom(bp.getClass())) {
					PropertyValues pvsToUse = ((InstantiationAwareBeanPostProcessor) bp).postProcessProperties(pvs, instanceWrapper, name);
					if (pvsToUse != null) {
						pvs = pvsToUse;
					}
				}
			}
		}
		if (pvs != null) {
			applyPropertyValues(name, beanDefinition, instanceWrapper, pvs);
		}
	}

	/**
	 * @param bean 真实对象
	 */
	protected void invokeInitMethods(String beanName, Object bean, BeanDefinition mbd) throws Throwable {
		boolean isInitializingBean = (bean instanceof InitializingBean);
		if (isInitializingBean && mbd != null) {
			if (log.isTraceEnabled()) {
				log.trace("Invoking afterPropertiesSet() on bean with name '{}'", beanName);
			}
			((InitializingBean) bean).afterPropertiesSet();
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
			log.trace("Invoking init method '{}' on bean with name '{}'", initMethodName, beanName);
		}
		try {
			ReflectionUtils.makeAccessible(initMethod);
			initMethod.invoke(bean);
		} catch (InvocationTargetException ex) {
			throw ex.getTargetException();
		}
	}

	/**
	 * 普通属性的注入
	 */
	private void applyPropertyValues(String name, BeanDefinition beanDefinition, Object bean, PropertyValues pvs) {
		try {
			for (PropertyValue propertyValue : pvs.getPropertyValues()) {
				Object value = propertyValue.getValue();
				if (value instanceof BeanReference) {
					BeanReference beanReference = (BeanReference) value;
					value = getBean(beanReference.getBeanName());
				}
				String propertyName = propertyValue.getName();
				try {
//					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(propertyName, bean.getClass());
//					Method writeMethod = propertyDescriptor.getWriteMethod();
//					writeMethod.invoke(bean, value);

					// setter 注入 set + 首字母大写 + 后续字母
					String setterName = "set" + propertyName.substring(0, 1).toUpperCase(ENGLISH) + propertyName.substring(1);
					Method declaredMethod = bean.getClass().getDeclaredMethod(setterName, value.getClass());
					ReflectionUtils.makeAccessible(declaredMethod);
					declaredMethod.invoke(bean, value);
				} catch (NoSuchMethodException e) {
					// Field 注入
					Field declaredField = bean.getClass().getDeclaredField(propertyName);
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
			return getInstantiationStrategy().instantiate(mbd, beanName, this);
		} catch (Throwable ex) {
			throw new BeanCreationException("Instantiation of bean named " + beanName + " failed", ex);
		}
	}

	public InstantiationStrategy getInstantiationStrategy() {
		return this.instantiationStrategy;
	}

	@Override
	public Object initializeBean(String beanName, Object bean, BeanDefinition mbd) {
		invokeAwareMethods(beanName, bean);
		Object wrappedBean = bean;
		wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
		try {
			invokeInitMethods(beanName, wrappedBean, mbd);
		} catch (Throwable ex) {
			throw new BeanCreationException("Invocation of init method failed", ex);
		}
		return applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
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
