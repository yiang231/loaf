package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * 允许调用 ConfigurableListableBeanFactory 及其父接口的方法调整 BeanFactory
 */
@FunctionalInterface
public interface BeanFactoryPostProcessor {
	void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}