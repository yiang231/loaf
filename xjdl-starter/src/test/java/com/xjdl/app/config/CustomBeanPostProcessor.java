package com.xjdl.app.config;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.config.BeanPostProcessor;
import com.xjdl.framework.context.ApplicationContext;
import com.xjdl.framework.context.ApplicationContextAware;
import com.xjdl.framework.context.ApplicationStartupAware;
import com.xjdl.framework.context.ResourceLoaderAware;
import com.xjdl.framework.core.io.ResourceLoader;
import com.xjdl.framework.core.metrics.ApplicationStartup;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class CustomBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware, ApplicationStartupAware, ResourceLoaderAware {
	private ApplicationContext applicationContext;
	private ApplicationStartup applicationStartup;
	private ResourceLoader resourceLoader;

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

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void setApplicationStartup(ApplicationStartup applicationStartup) {
		this.applicationStartup = applicationStartup;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
}
