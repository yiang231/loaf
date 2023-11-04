package com.xjdl.app.service;

import com.xjdl.framework.beans.factory.BeanFactoryAware;
import com.xjdl.framework.beans.factory.BeanNameAware;
import com.xjdl.framework.beans.factory.ComponentFactory;
import com.xjdl.framework.beans.factory.InitializingBean;
import com.xjdl.framework.beans.factory.config.BeanPostProcessor;
import com.xjdl.framework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
//@Scope(ScopeType.prototype)
public class AdapterService implements BeanNameAware, BeanFactoryAware, BeanPostProcessor, InitializingBean, BaseService {
	static {
		System.out.println("AdapterService.static initializer");
	}

	private String beanName;
	private ComponentFactory factory;

	@Override
	public Object postProcessBeforeInitialize(String name, Object bean) {
		System.out.println("AdapterService.postProcessBeforeInitialize");
		return bean;
	}

	@Override
	public Object postProcessAfterInitialize(String name, Object bean) {
		System.out.println(name + ".postProcessAfterInitialize");
		if (bean instanceof BaseService) {
			Object instance = Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{BaseService.class}, new InvocationHandler() {
				@Override
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					System.out.println(name + " invoke proxy method");
					return method.invoke(bean, args);
				}
			});
			return instance;
		}
		return bean;
	}

	@Override
	public void afterPropertiesSet() {
		System.out.println("AdapterService.afterPropertiesSet");
	}

	@Override
	public void setBeanFactory(ComponentFactory beanFactory) {
		System.out.println("AdapterService.setBeanFactory");
		this.factory = beanFactory;
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("AdapterService.setBeanName");
		this.beanName = name;
	}

	@Override
	public void test() {
		System.out.println(beanName);
	}
}
