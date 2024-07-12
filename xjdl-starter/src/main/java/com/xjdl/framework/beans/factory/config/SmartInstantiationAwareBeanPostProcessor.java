package com.xjdl.framework.beans.factory.config;

import com.xjdl.framework.beans.BeansException;

public interface SmartInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessor{
	default Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
		return bean;
	}
}
