package com.xjdl.framework.context.support;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.support.DefaultListableBeanFactory;
import com.xjdl.framework.beans.factory.xml.XmlBeanDefinitionReader;
import com.xjdl.framework.context.ApplicationContext;
import com.xjdl.framework.core.io.Resource;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableConfigApplicationContext {
	public AbstractXmlApplicationContext(ApplicationContext parent) {
		super(parent);
	}

	@Override
	protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException {
		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
		beanDefinitionReader.setResourceLoader(this);
		loadBeanDefinitions(beanDefinitionReader);
	}

	protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws BeansException {
		Resource[] configResources = getConfigResources();
		if (configResources != null) {
			reader.loadBeanDefinitions(configResources);
		}
		String[] configLocations = getConfigLocations();
		if (configLocations != null) {
			reader.loadBeanDefinitions(configLocations);
		}
	}

	/**
	 * 被重写为获取 resources 目录下的 XML 文件
	 */
	protected Resource[] getConfigResources() {
		return null;
	}
}
