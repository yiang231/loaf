package com.xjdl.framework.context.support;


import com.xjdl.framework.beans.factory.support.AbstractComponentFactory;
import com.xjdl.framework.beans.factory.support.BeanDefinition;
import com.xjdl.framework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * 可以视作 AbstractComponentFactory 的代理
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
	private AbstractComponentFactory factory;

	public AbstractApplicationContext(AbstractComponentFactory factory) {
		this.factory = factory;
	}

	public AbstractComponentFactory getFactory() {
		return factory;
	}

	@Override
	public Object getComponent(String componentName) {
		return this.factory.getComponent(componentName);
	}

	protected abstract void loadBeanDefinitions(AbstractComponentFactory beanFactory) throws Exception;

	@Override
	public void refresh() throws Exception {
		this.loadBeanDefinitions(factory);
		System.out.println("[Singleton Component initial start]");
		this.initComponent(factory);
		System.out.println("[Singleton Component initial end]");
		this.log();
	}

	public void log() {
		this.logBeanDefinitionMap("BeanDefinition信息");
		this.logSingletonObjects("单例池中的对象");
		this.logSingletonComponentName("单例组件名");
		this.logBeanPostProcessors("实现后置处理器的组件名");
	}

	public void logBeanPostProcessors(String description) {
		System.out.println(description);
//        for (String item : this.getFactory().getBeanPostProcessors()) {
//            System.out.println("\t[" + item + "]");
//        }
		System.out.println("\t" + this.getFactory().getBeanPostProcessors());
	}

	public void logSingletonComponentName(String description) {
		System.out.println(description);
//        for (String item : context.getFactory().getSingletonComponentName()) {
//            System.out.println("\t[" + item + "]");
//        }
		System.out.println("\t" + this.getFactory().getSingletonComponentName());
	}

	public void logSingletonObjects(String description) {
		System.out.println(description);
		for (Map.Entry<String, Object> entry : this.getFactory().getSingletonObjects().entrySet()) {
			System.out.println("\t[" + entry + "]");
		}
	}

	public void logBeanDefinitionMap(String description) {
		System.out.println(description);
		for (Map.Entry<String, BeanDefinition> entry : this.getFactory().getBeanDefinitionMap().entrySet()) {
			System.out.println("\t[" + entry + "]");
		}
	}

	private void initComponent(AbstractComponentFactory factory) {
		List<String> singletonComponentName = factory.getSingletonComponentName();
		for (String name : singletonComponentName) {
			this.getComponent(name);
		}
	}
}

