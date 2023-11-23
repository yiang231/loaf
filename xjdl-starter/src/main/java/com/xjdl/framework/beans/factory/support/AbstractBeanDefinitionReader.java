package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.factory.BeanDefinitionStoreException;
import com.xjdl.framework.core.io.DefaultResourceLoader;
import com.xjdl.framework.core.io.Resource;
import com.xjdl.framework.core.io.ResourceLoader;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
	private final BeanDefinitionRegistry registry;
	private ResourceLoader resourceLoader;
	private BeanNameGenerator beanNameGenerator = DefaultBeanNameGenerator.INSTANCE;

	public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
		this.registry = registry;
		this.resourceLoader = resourceLoader;
	}

	public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
		this(registry, new DefaultResourceLoader());
	}

	public BeanNameGenerator getBeanNameGenerator() {
		return beanNameGenerator;
	}

	public void setBeanNameGenerator(BeanNameGenerator beanNameGenerator) {
		this.beanNameGenerator = (beanNameGenerator != null ? beanNameGenerator : DefaultBeanNameGenerator.INSTANCE);
	}

	@Override
	public BeanDefinitionRegistry getRegistry() {
		return this.registry;
	}

	@Override
	public ResourceLoader getResourceLoader() {
		return this.resourceLoader;
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@Override
	public ClassLoader getBeanClassLoader() {
		return resourceLoader.getClassLoader();
	}

	@Override
	public int loadBeanDefinitions(String... locations) throws BeanDefinitionStoreException {
		int count = 0;
		for (String location : locations) {
			count += loadBeanDefinitions(location);
		}
		return count;
	}

	@Override
	public int loadBeanDefinitions(Resource... resources) throws BeanDefinitionStoreException {
		int count = 0;
		for (Resource resource : resources) {
			count += loadBeanDefinitions(resource);
		}
		return count;
	}
}
