package com.xjdl.app.config;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.config.BeanPostProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomBeanPostProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		log.debug("{}.postProcessBeforeInitialization", beanName);
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		log.debug("{}.postProcessAfterInitialization", beanName);
		return bean;
	}
}
