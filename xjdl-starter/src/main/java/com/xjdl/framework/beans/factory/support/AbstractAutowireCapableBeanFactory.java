package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.factory.BeanCreationException;
import com.xjdl.framework.beans.factory.config.BeanDefinition;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

	@Override
	protected Object creatBean(String name, BeanDefinition beanDefinition) {
		return doCreateBean(name, beanDefinition);
	}

	private Object doCreateBean(String name, BeanDefinition beanDefinition) {
		Object bean;
		try {
			bean = beanDefinition.getBeanClass().newInstance();
		} catch (Exception e) {
			throw new BeanCreationException("Instantiation of bean named '" + name + "' failed", e);
		}
		super.registerSingleton(name, bean);
		return bean;
	}
}
