package com.xjdl.app.config;

import com.xjdl.app.service.HelloWorldService;
import com.xjdl.app.service.OutputService;
import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.BeanClassLoaderAware;
import com.xjdl.framework.beans.factory.BeanFactory;
import com.xjdl.framework.beans.factory.BeanFactoryAware;
import com.xjdl.framework.beans.factory.BeanNameAware;
import com.xjdl.framework.beans.factory.config.BeanDefinition;
import com.xjdl.framework.beans.factory.config.ConfigurableListableBeanFactory;
import com.xjdl.framework.beans.factory.support.BeanDefinitionRegistry;
import com.xjdl.framework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import lombok.Getter;

@Getter
public class CustomBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, BeanFactoryAware, BeanClassLoaderAware, BeanNameAware {
	private BeanFactory beanFactory;
	private String name;
	private ClassLoader classLoader;
	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	public void setBeanName(String name) {
		this.name = name;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		BeanDefinition beanDefinition = new BeanDefinition();
		beanDefinition.setBeanClass(HelloWorldService.class);

		registry.registerBeanDefinition("helloWorldService0", beanDefinition);
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		beanFactory.registerSingleton("outputService0", new OutputService());
	}
}
