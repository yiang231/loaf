package com.xjdl.framework.context.support;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.config.BeanPostProcessor;
import com.xjdl.framework.context.ApplicationContextAware;
import com.xjdl.framework.context.ApplicationEventPublisherAware;
import com.xjdl.framework.context.ApplicationStartupAware;
import com.xjdl.framework.context.ConfigurableApplicationContext;
import com.xjdl.framework.context.ResourceLoaderAware;

/**
 * 批量处理多个 ApplicationContext 的感知
 */
class ApplicationContextAwareProcessor implements BeanPostProcessor {
	private final ConfigurableApplicationContext applicationContext;

	public ApplicationContextAwareProcessor(ConfigurableApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (!(bean instanceof ResourceLoaderAware || bean instanceof ApplicationEventPublisherAware ||
				bean instanceof ApplicationContextAware || bean instanceof ApplicationStartupAware)) {
			return bean;
		}

		invokeAwareInterfaces(bean);
		return bean;
	}

	private void invokeAwareInterfaces(Object bean) {
		if (bean instanceof ResourceLoaderAware) {
			((ResourceLoaderAware) bean).setResourceLoader(this.applicationContext);
		}
		if (bean instanceof ApplicationEventPublisherAware) {
			((ApplicationEventPublisherAware) bean).setApplicationEventPublisher(this.applicationContext);
		}
		if (bean instanceof ApplicationStartupAware) {
			((ApplicationStartupAware) bean).setApplicationStartup(this.applicationContext.getApplicationStartup());
		}
		if (bean instanceof ApplicationContextAware) {
			((ApplicationContextAware) bean).setApplicationContext(this.applicationContext);
		}
	}
}
