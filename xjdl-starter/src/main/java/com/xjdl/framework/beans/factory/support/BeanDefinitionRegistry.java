package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.factory.BeanDefinitionStoreException;
import com.xjdl.framework.beans.factory.NoSuchBeanDefinitionException;
import com.xjdl.framework.beans.factory.config.BeanDefinition;

/**
 * 注册 BeanDefinition 的接口
 */
public interface BeanDefinitionRegistry {
	void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionStoreException;

	BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;
}
