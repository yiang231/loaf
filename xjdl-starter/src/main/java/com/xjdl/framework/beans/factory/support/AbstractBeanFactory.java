package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.BeanFactory;
import com.xjdl.framework.beans.factory.config.BeanDefinition;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
	@Override
	public Object getBean(String name) throws BeansException {
		Object bean = super.getSingleton(name);
		if (bean != null) {
			return bean;
		}
		BeanDefinition beanDefinition = getBeanDefinition(name);
		return creatBean(name, beanDefinition);
	}

	/**
	 * 从 AbstractAutowireCapableBeanFactory 创建 Bean
	 * <p>
	 * 当前简化 BeanDefinition 仅包含 bean 的 class
	 */
	protected abstract Object creatBean(String name, BeanDefinition beanDefinition);

	/**
	 * 从 DefaultListableBeanFactory 获取 BeanDefinition
	 */
	protected abstract BeanDefinition getBeanDefinition(String name);
}
