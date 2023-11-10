package com.xjdl.framework.beans.factory.support;


import com.xjdl.framework.beans.factory.config.BeanDefinition;
import com.xjdl.framework.core.io.ResourceLoader;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
	private ResourceLoader resourceLoader;
	private Set<String> registryComponentClassesSet;
	private Map<String, BeanDefinition> registryBeanDefinition;

	public AbstractBeanDefinitionReader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
		this.registryComponentClassesSet = new LinkedHashSet<String>();
		this.registryBeanDefinition = new ConcurrentHashMap<String, BeanDefinition>();
	}

	public abstract void loadBeanDefinitions(String scanPackage) throws Exception;

	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	public Map<String, BeanDefinition> getRegistryBeanDefinition() {
		return registryBeanDefinition;
	}

	public Set<String> getRegistryComponentClassesSet() {
		return registryComponentClassesSet;
	}

	@Override
	public boolean containsBeanDefinition(String beanName) {
		return registryBeanDefinition.containsKey(beanName);
	}

	@Override
	public int getBeanDefinitionCount() {
		return registryBeanDefinition.size();
	}

	@Override
	public BeanDefinition getBeanDefinition(String beanName) {
		return registryBeanDefinition.get(beanName);
	}

	@Override
	public void removeBeanDefinition(String beanName) {
		registryBeanDefinition.remove(beanName);
	}
}
