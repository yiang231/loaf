package com.xjdl.framework.beans.factory.config;

import com.xjdl.framework.beans.BeansException;

/**
 * Bean 执行初始化方法前后的操作
 */
public interface BeanPostProcessor {
	Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

	Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
