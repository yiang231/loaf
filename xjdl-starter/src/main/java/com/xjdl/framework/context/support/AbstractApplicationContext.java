package com.xjdl.framework.context.support;


import com.xjdl.framework.beans.factory.support.AbstractComponentFactory;
import com.xjdl.framework.beans.factory.config.BeanDefinition;
import com.xjdl.framework.context.ApplicationContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * 可以视作 AbstractComponentFactory 的代理
 */
@Slf4j
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
		log.debug("[Singleton Component initial start]");
		this.initComponent(factory);
		log.debug("[Singleton Component initial end]");
		this.log();
	}

	public void log() {
		this.logBeanDefinitionMap("BeanDefinition信息");
		this.logSingletonObjects("单例池中的对象");
		this.logSingletonComponentName("单例组件名");
		this.logBeanPostProcessors("实现后置处理器的组件名");
	}

	public void logBeanPostProcessors(String description) {
		log.debug(description);
//		for (String item : this.getFactory().getBeanPostProcessors()) {
//			log.debug("\t[{}]", item);
//		}
		log.debug("\t{}", this.getFactory().getBeanPostProcessors());
	}

	public void logSingletonComponentName(String description) {
		log.debug(description);
//		for (String item : this.getFactory().getSingletonComponentName()) {
//			log.debug("\t[{}]", item);
//		}
		log.debug("\t{}", this.getFactory().getSingletonComponentName());
	}

	public void logSingletonObjects(String description) {
		log.debug(description);
		for (Map.Entry<String, Object> entry : this.getFactory().getSingletonObjects().entrySet()) {
			log.debug("\t[{}]", entry);
		}
	}

	public void logBeanDefinitionMap(String description) {
		log.debug(description);
		for (Map.Entry<String, BeanDefinition> entry : this.getFactory().getBeanDefinitionMap().entrySet()) {
			log.debug("\t[{}]", entry);
		}
	}

	private void initComponent(AbstractComponentFactory factory) {
		List<String> singletonComponentName = factory.getSingletonComponentName();
		for (String name : singletonComponentName) {
			this.getComponent(name);
		}
	}
}

