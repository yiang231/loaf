package com.xjdl.framework.beans.factory.support;


import com.xjdl.framework.beans.factory.config.BeanDefinition;

public interface BeanDefinitionReader {
	void loadBeanDefinitions(String scanPackage) throws Exception;

	void removeBeanDefinition(String beanName);

	BeanDefinition getBeanDefinition(String beanName);

	boolean containsBeanDefinition(String beanName);

	int getBeanDefinitionCount();
}
