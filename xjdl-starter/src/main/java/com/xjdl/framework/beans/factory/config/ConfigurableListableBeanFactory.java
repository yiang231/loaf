package com.xjdl.framework.beans.factory.config;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.ListableBeanFactory;
import com.xjdl.framework.beans.factory.NoSuchBeanDefinitionException;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
	BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;

	/**
	 * 提供容器刷新时的预实例化单例 bean 的方法
	 */
	void preInstantiateSingletons() throws BeansException;
}
