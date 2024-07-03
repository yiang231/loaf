package com.xjdl.framework.aop.framework.adapter;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.config.BeanPostProcessor;

public class AdvisorAdapterRegistrationManager implements BeanPostProcessor {
	private AdvisorAdapterRegistry advisorAdapterRegistry = GlobalAdvisorAdapterRegistry.getInstance();

	public void setAdvisorAdapterRegistry(AdvisorAdapterRegistry advisorAdapterRegistry) {
		this.advisorAdapterRegistry = advisorAdapterRegistry;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof AdvisorAdapter) {
			this.advisorAdapterRegistry.registerAdvisorAdapter((AdvisorAdapter) bean);
		}
		return bean;
	}
}
