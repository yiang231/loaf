package com.xjdl.framework.beans.factory.config;

/**
 * 注册单例 bean 的接口
 */
public interface SingletonBeanRegistry {
	Object getSingleton(String beanName);

	void registerSingleton(String beanName, Object singletonObject);

	boolean containsSingleton(String beanName);

	Object getSingletonMutex();
}
