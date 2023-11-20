package com.xjdl.framework.beans.factory.config;

import com.xjdl.framework.beans.MutablePropertyValues;
import com.xjdl.framework.beans.PropertyValues;

/**
 * Bean 的信息定义
 */
public class BeanDefinition {
	private Class<?> beanClass;
	private PropertyValues propertyValues = new MutablePropertyValues();

	public PropertyValues getPropertyValues() {
		return propertyValues;
	}

	public void setPropertyValues(PropertyValues propertyValues) {
		this.propertyValues = propertyValues;
	}

	public Class<?> getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}
}

