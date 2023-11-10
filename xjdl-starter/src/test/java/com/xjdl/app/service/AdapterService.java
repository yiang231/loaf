package com.xjdl.app.service;

import com.xjdl.framework.beans.factory.BeanFactoryAware;
import com.xjdl.framework.beans.factory.BeanNameAware;
import com.xjdl.framework.beans.factory.ComponentFactory;
import com.xjdl.framework.beans.factory.InitializingBean;
import com.xjdl.framework.beans.factory.config.BeanPostProcessor;
import com.xjdl.framework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
@Slf4j
//@Scope(ScopeType.prototype)
public class AdapterService implements BeanNameAware, BeanFactoryAware, BeanPostProcessor, InitializingBean, BaseService {
	static {
		log.info("AdapterService.static initializer");
	}

	private String beanName;
	private ComponentFactory factory;

	@Override
	public Object postProcessBeforeInitialize(String name, Object bean) {
		log.info("AdapterService.postProcessBeforeInitialize");
		return bean;
	}

	@Override
	public Object postProcessAfterInitialize(String name, Object bean) {
		log.info("{}.postProcessAfterInitialize", name);
		if (bean instanceof BaseService) {
			Object instance = Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{BaseService.class}, new InvocationHandler() {
				@Override
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					log.info("{} invoke proxy method", name);
					return method.invoke(bean, args);
				}
			});
			return instance;
		}
		return bean;
	}

	@Override
	public void afterPropertiesSet() {
		log.info("AdapterService.afterPropertiesSet");
	}

	@Override
	public void setBeanFactory(ComponentFactory beanFactory) {
		log.info("AdapterService.setBeanFactory");
		this.factory = beanFactory;
	}

	@Override
	public void setBeanName(String name) {
		log.info("AdapterService.setBeanName");
		this.beanName = name;
	}

	@Override
	public void test() {
		log.info(beanName);
	}
}
