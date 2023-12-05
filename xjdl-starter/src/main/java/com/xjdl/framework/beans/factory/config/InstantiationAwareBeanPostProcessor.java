package com.xjdl.framework.beans.factory.config;

import com.xjdl.framework.beans.BeansException;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
	default Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		return null;
	}
}
