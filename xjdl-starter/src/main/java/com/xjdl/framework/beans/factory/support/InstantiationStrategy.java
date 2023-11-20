package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.BeanFactory;
import com.xjdl.framework.beans.factory.config.BeanDefinition;

/**
 * Bean 的实例化策略
 */
public interface InstantiationStrategy {
	Object instantiate(BeanDefinition bd, String beanName, BeanFactory owner) throws BeansException;
}
