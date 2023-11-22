package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.PropertyValue;
import com.xjdl.framework.beans.factory.Aware;
import com.xjdl.framework.beans.factory.BeanClassLoaderAware;
import com.xjdl.framework.beans.factory.BeanCreationException;
import com.xjdl.framework.beans.factory.BeanFactoryAware;
import com.xjdl.framework.beans.factory.BeanNameAware;
import com.xjdl.framework.beans.factory.config.AutowireCapableBeanFactory;
import com.xjdl.framework.beans.factory.config.BeanDefinition;
import com.xjdl.framework.beans.factory.config.BeanPostProcessor;
import com.xjdl.framework.beans.factory.config.BeanReference;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Slf4j
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
	private final InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

	@Override
	protected Object creatBean(String beanName, BeanDefinition beanDefinition) {
		Object beanInstance = doCreateBean(beanName, beanDefinition);
		if (log.isTraceEnabled()) {
			log.trace("Finished creating instance of bean '" + beanName + "'");
		}
		return beanInstance;
	}

	private Object doCreateBean(String name, BeanDefinition beanDefinition) {
		Object bean = createBeanInstance(beanDefinition, name);
		Object exposedObject = bean;
		try {
			populateBean(exposedObject, beanDefinition, name);
			exposedObject = initializeBean(exposedObject, name);
		} catch (Exception e) {
			throw new BeanCreationException("Instantiation of bean named '" + name + "' failed", e);
		}
		super.registerSingleton(name, exposedObject);
		return exposedObject;
	}

	private void populateBean(Object exposedObject, BeanDefinition beanDefinition, String name) {
		applyPropertyValues(exposedObject, beanDefinition, name);
	}

    /**
     * 普通属性注入
     */
    private void applyPropertyValues(Object bean, BeanDefinition beanDefinition, String name) {
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
	public Object initializeBean(Object existingBean, String beanName) throws BeansException {
		return initializeBean(beanName, existingBean, null);
	}

	protected Object initializeBean(String beanName, Object bean, BeanDefinition mbd) {
		invokeAwareMethods(beanName, bean);
		applyBeanPostProcessorsBeforeInitialization(bean, beanName);
		try {
			invokeInitMethods(beanName, bean, mbd);
		} catch (Throwable ex) {
			throw new BeanCreationException("Invocation of init method failed", ex);
		}
		applyBeanPostProcessorsAfterInitialization(bean, beanName);
		return bean;
	}

	protected void invokeInitMethods(String beanName, Object bean, BeanDefinition mbd) throws Throwable {
		// TODO 真正执行初始化方法 InitializingBean.afterPropertiesSet
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
