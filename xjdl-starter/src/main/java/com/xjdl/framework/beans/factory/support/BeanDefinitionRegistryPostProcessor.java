package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.BeansException;

/**
 * 允许调用 BeanDefinitionRegistry 接口中的方法，设置 BeanDefinition 相关属性
 */
public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor {
	void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException;
}