package com.xjdl.framework.beans.factory.config;

import com.xjdl.framework.beans.factory.BeanFactory;
import com.xjdl.framework.beans.factory.HierarchicalBeanFactory;
import com.xjdl.framework.core.metrics.ApplicationStartup;

/**
 * 为容器配置属性
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
	String SCOPE_SINGLETON = "singleton";
	String SCOPE_PROTOTYPE = "prototype";

	/**
	 * 允许添加 BeanPostProcessor
	 */
	void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

	void setParentBeanFactory(BeanFactory parentBeanFactory) throws IllegalStateException;

	//	void destroySingletons();

	/**
	 * 刷新容器时的准备阶段使用，为 BeanFactory 设置类加载器
	 */
	void setBeanClassLoader(ClassLoader beanClassLoader);

	/**
	 * 刷新容器失败，关闭容器时销毁已创建的 Bean 对象
	 */
	void destroySingletons();

	ApplicationStartup getApplicationStartup();
}
