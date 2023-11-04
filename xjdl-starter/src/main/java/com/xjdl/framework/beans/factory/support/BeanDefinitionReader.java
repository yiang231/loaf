package com.xjdl.framework.beans.factory.support;


public interface BeanDefinitionReader {
	void loadBeanDefinitions(String scanPackage) throws Exception;

	void removeBeanDefinition(String beanName);

	BeanDefinition getBeanDefinition(String beanName);

	boolean containsBeanDefinition(String beanName);

	int getBeanDefinitionCount();
}
