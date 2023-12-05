package com.xjdl.framework.beans.factory;

import com.xjdl.framework.beans.BeansException;

/**
 * Bean的工厂接口，用于获取 Bean
 */
public interface BeanFactory {
	Object getBean(String name) throws BeansException;
	boolean containsBean(String name);
}
