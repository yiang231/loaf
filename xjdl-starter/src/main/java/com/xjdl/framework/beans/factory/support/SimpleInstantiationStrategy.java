package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.BeanInstantiationException;
import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.BeanFactory;
import com.xjdl.framework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

public class SimpleInstantiationStrategy implements InstantiationStrategy {
	/**
	 * TODO 后续实现有参构造器的对象创建
	 */
	@Override
	public Object instantiate(BeanDefinition bd, String beanName, BeanFactory owner) throws BeansException {
		try {
			Class<?> beanClass = bd.getBeanClass();
			Constructor<?> constructor = beanClass.getDeclaredConstructor();
			return constructor.newInstance();
		} catch (Exception e) {
			throw new BeanInstantiationException("Failed to instantiate [" + beanName + "]", e);
		}
	}
}
