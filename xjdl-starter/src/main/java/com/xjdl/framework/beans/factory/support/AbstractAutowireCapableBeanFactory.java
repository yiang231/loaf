package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.PropertyValue;
import com.xjdl.framework.beans.factory.BeanCreationException;
import com.xjdl.framework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
	private final InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

	@Override
	protected Object creatBean(String name, BeanDefinition beanDefinition) {
		return doCreateBean(name, beanDefinition);
	}

	private Object doCreateBean(String name, BeanDefinition beanDefinition) {
		Object bean;
		try {
			bean = createBeanInstance(beanDefinition, name);
			applyPropertyValues(bean, beanDefinition, name);
		} catch (Exception e) {
			throw new BeanCreationException("Instantiation of bean named '" + name + "' failed", e);
		}
		super.registerSingleton(name, bean);
		return bean;
	}

	/**
	 * 普通属性注入
	 */
	private void applyPropertyValues(Object bean, BeanDefinition beanDefinition, String name) {
		try {
			for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()) {
				Object value = propertyValue.getValue();
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
		return getInstantiationStrategy().instantiate(beanDefinition, name, this);
	}

	public InstantiationStrategy getInstantiationStrategy() {
		return this.instantiationStrategy;
	}
}
