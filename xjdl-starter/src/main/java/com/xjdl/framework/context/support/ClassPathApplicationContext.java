package com.xjdl.framework.context.support;


import com.xjdl.framework.beans.factory.support.AbstractComponentFactory;
import com.xjdl.framework.beans.factory.support.BeanDefinition;
import com.xjdl.framework.beans.factory.support.ClassPathBeanDefinitionReader;
import com.xjdl.framework.beans.factory.support.ClassPathComponentFactory;
import com.xjdl.framework.beans.factory.xml.XmlBeanDefinitionReader;
import com.xjdl.framework.context.annotation.ComponentScan;
import com.xjdl.framework.core.io.ResourceLoader;

import java.util.HashMap;
import java.util.Map;

public class ClassPathApplicationContext extends AbstractApplicationContext {
	public static final String DEFAULT_APPLICATION_XML = "application.xml";
	private final String scanPackage;
	private String baseUri;

	public ClassPathApplicationContext(Class<?> config, AbstractComponentFactory factory) {
		super(factory);
		this.scanPackage = config.getAnnotation(ComponentScan.class).value();
//		this.baseUri = config.getAnnotation(BaseUri.class).value();
	}

	public ClassPathApplicationContext(Class<?> config) {
		this(config, new ClassPathComponentFactory());
	}

	public String getBaseUri() {
		return baseUri;
	}

	/**
	 * BeanDefinitionReader 负责辅助 AbstractComponentFactory 扫描组件，装载 BeanDefinition ，这里不将两者进行绑定
	 */
	@Override
	protected void loadBeanDefinitions(AbstractComponentFactory beanFactory) throws Exception {
		Map<String, BeanDefinition> reader = getAllReader(new ResourceLoader());
		for (Map.Entry<String, BeanDefinition> entry : reader.entrySet()) {
			beanFactory.registerBeanDefinition(entry.getKey(), entry.getValue());
		}
	}

	public Map<String, BeanDefinition> getAllReader(ResourceLoader resourceLoader) throws Exception {
		ClassPathBeanDefinitionReader classPathBeanDefinitionReader = new ClassPathBeanDefinitionReader(resourceLoader);
		classPathBeanDefinitionReader.loadBeanDefinitions(scanPackage);

		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(resourceLoader);
		xmlBeanDefinitionReader.loadBeanDefinitions(DEFAULT_APPLICATION_XML);

		Map<String, BeanDefinition> reader = new HashMap<>();
		reader.putAll(classPathBeanDefinitionReader.getRegistryBeanDefinition());
		reader.putAll(xmlBeanDefinitionReader.getRegistryBeanDefinition());
		return reader;
	}
}
