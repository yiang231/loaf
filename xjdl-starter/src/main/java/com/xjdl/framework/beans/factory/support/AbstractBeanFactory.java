package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.config.BeanDefinition;
import com.xjdl.framework.beans.factory.config.BeanPostProcessor;
import com.xjdl.framework.beans.factory.config.ConfigurableBeanFactory;
import com.xjdl.framework.util.ClassUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {
	private final List<BeanPostProcessor> beanPostProcessors = new CopyOnWriteArrayList<>();
	private final ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

	@Override
	public Object getBean(String name) throws BeansException {
		Object bean = super.getSingleton(name);
		if (bean != null) {
			return bean;
		}
		BeanDefinition beanDefinition = getBeanDefinition(name);
		return creatBean(name, beanDefinition);
	}

	/**
	 * 从 AbstractAutowireCapableBeanFactory 创建 Bean
	 * <p>
	 * 当前简化 BeanDefinition 仅包含 bean 的 class
	 */
	protected abstract Object creatBean(String name, BeanDefinition beanDefinition);

	/**
	 * 从 DefaultListableBeanFactory 获取 BeanDefinition
	 */
	protected abstract BeanDefinition getBeanDefinition(String name);

	@Override
	public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
		synchronized (this.beanPostProcessors) {
			this.beanPostProcessors.remove(beanPostProcessor);
			this.beanPostProcessors.add(beanPostProcessor);
		}
	}

	public List<BeanPostProcessor> getBeanPostProcessors() {
		return beanPostProcessors;
	}

	public ClassLoader getBeanClassLoader() {
		return this.beanClassLoader;
	}
}
