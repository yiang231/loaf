package com.xjdl.framework.context;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.config.ConfigurableListableBeanFactory;
import com.xjdl.framework.beans.factory.support.BeanFactoryPostProcessor;
import com.xjdl.framework.core.metrics.ApplicationStartup;

import java.io.Closeable;

public interface ConfigurableApplicationContext extends ApplicationContext, Closeable {
	String APPLICATION_STARTUP_BEAN_NAME = "applicationStartup";
	String SHUTDOWN_HOOK_THREAD_NAME = "XjdlContextShutdownHook";

	void refresh() throws BeansException, IllegalStateException;

	/**
	 * 从 AbstractRefreshableApplicationContext 获得 BeanFactory 的底层实现
	 */
	ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

	/**
	 * 允许添加 BeanFactoryPostProcessor
	 */
	void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);

	void setId(String id);

	void setParent(ApplicationContext parent);

	boolean isActive();

	ApplicationStartup getApplicationStartup();

	/**
	 * JVM 关闭时关闭容器
	 */
	void registerShutdownHook();

	/**
	 * 直接关闭容器
	 */
	@Override
	void close();
}
