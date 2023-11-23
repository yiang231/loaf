package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.factory.BeanDefinitionStoreException;
import com.xjdl.framework.core.io.Resource;
import com.xjdl.framework.core.io.ResourceLoader;

/**
 * 获取 BeanDefinition 信息的接口
 */
public interface BeanDefinitionReader {
	/**
	 * 关键方法
	 */
	BeanDefinitionRegistry getRegistry();

	ResourceLoader getResourceLoader();

	ClassLoader getBeanClassLoader();

	/**
	 * 最终实现
	 */
	int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException;

	int loadBeanDefinitions(Resource... resources) throws BeanDefinitionStoreException;

	int loadBeanDefinitions(String location) throws BeanDefinitionStoreException;

	int loadBeanDefinitions(String... locations) throws BeanDefinitionStoreException;
}
