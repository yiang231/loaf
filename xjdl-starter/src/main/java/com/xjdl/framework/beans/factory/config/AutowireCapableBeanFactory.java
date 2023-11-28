package com.xjdl.framework.beans.factory.config;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.BeanFactory;

/**
 * Bean 创建时主要执行的方法
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
	Object initializeBean(String beanName, Object existingBean, BeanDefinition mbd) throws BeansException;

	Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

	Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
