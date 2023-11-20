package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.factory.BeanCreationException;
import com.xjdl.framework.beans.factory.config.BeanDefinition;

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
		} catch (Exception e) {
			throw new BeanCreationException("Instantiation of bean named '" + name + "' failed", e);
		}
		super.registerSingleton(name, bean);
		return bean;
	}

	private Object createBeanInstance(BeanDefinition beanDefinition, String name) {
		return getInstantiationStrategy().instantiate(beanDefinition, name, this);
	}

	public InstantiationStrategy getInstantiationStrategy() {
		return this.instantiationStrategy;
	}
}
