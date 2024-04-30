package com.xjdl.framework.beans.factory.config;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.PropertyValues;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
	default PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
		return null;
	}
}
