package com.xjdl.framework.context.support;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.context.ApplicationContext;
import com.xjdl.framework.core.io.ClassPathResource;
import com.xjdl.framework.core.io.Resource;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {
	private Resource[] configResources;

	public ClassPathXmlApplicationContext(String configLocation) throws BeansException {
		this(new String[]{configLocation}, null);
	}

	/**
	 * 注意：对于一般的 String 类型的资源地址，需要配置前缀以获取 Resource 类型的对象。而在 ClassPathXmlApplicationContext 中直接将其转换为 Resource 类型，故使用时不需要配置前缀
	 */
	public ClassPathXmlApplicationContext(String[] configLocations, ApplicationContext parent) throws BeansException {
		super(parent);
		this.configResources = new Resource[configLocations.length];
		for (int i = 0; i < configLocations.length; i++) {
			this.configResources[i] = new ClassPathResource(configLocations[i]);
		}
		refresh();
	}


	@Override
	protected Resource[] getConfigResources() {
		return this.configResources;
	}
}
