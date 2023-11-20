package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.factory.BeanDefinitionStoreException;
import com.xjdl.framework.beans.factory.NoSuchBeanDefinitionException;
import com.xjdl.framework.beans.factory.config.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {
	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionStoreException {
		this.beanDefinitionMap.put(beanName, beanDefinition);
	}

	@Override
	public BeanDefinition getBeanDefinition(String name) {
		BeanDefinition beanDefinition = beanDefinitionMap.get(name);
		if (beanDefinition == null) {
			throw new NoSuchBeanDefinitionException("No bean named '" + name + "' is defined");
		}
		return beanDefinition;
	}
}
