package com.xjdl.framework.context;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.config.ConfigurableListableBeanFactory;
import com.xjdl.framework.beans.factory.support.BeanFactoryPostProcessor;

public interface ConfigurableApplicationContext extends ApplicationContext {
	void refresh() throws BeansException, IllegalStateException;

	/**
	 * 从 AbstractRefreshableApplicationContext 获得 BeanFactory 的底层实现
	 */
	ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

	/**
	 * 允许添加 BeanFactoryPostProcessor
	 */
	void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);

	void setId(String id);

	void setParent(ApplicationContext parent);
}
