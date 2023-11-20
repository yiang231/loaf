package com.xjdl.framework.beans.factory.config;

/**
 * Bean 的信息定义
 */
public class BeanDefinition {
	private Class<?> beanClass;

	public Class<?> getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}
}

