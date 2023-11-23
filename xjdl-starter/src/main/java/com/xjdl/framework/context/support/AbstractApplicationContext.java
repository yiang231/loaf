package com.xjdl.framework.context.support;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.BeanFactory;
import com.xjdl.framework.beans.factory.config.AutowireCapableBeanFactory;
import com.xjdl.framework.beans.factory.config.BeanPostProcessor;
import com.xjdl.framework.beans.factory.config.ConfigurableListableBeanFactory;
import com.xjdl.framework.beans.factory.support.BeanFactoryPostProcessor;
import com.xjdl.framework.context.ApplicationContext;
import com.xjdl.framework.context.ConfigurableApplicationContext;
import com.xjdl.framework.core.io.DefaultResourceLoader;
import com.xjdl.framework.core.metrics.ApplicationStartup;
import com.xjdl.framework.core.metrics.StartupStep;
import com.xjdl.framework.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 面向使用者
 */
@Slf4j
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
	private final AtomicBoolean active = new AtomicBoolean();
	private final AtomicBoolean closed = new AtomicBoolean();
	private final Object startupShutdownMonitor = new Object();
	private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();
	private long startupDate;
	private ApplicationStartup applicationStartup = ApplicationStartup.DEFAULT;
	private ApplicationContext parent;
	private String id = ObjectUtils.identityToString(this);
	private String displayName = ObjectUtils.identityToString(this);

	public AbstractApplicationContext(ApplicationContext parent) {
		this();
		setParent(parent);
	}

	public AbstractApplicationContext() {
	}

	@Override
	public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
		return getBeanFactory();
	}

	@Override
	public void refresh() throws BeansException, IllegalStateException {
		synchronized (this.startupShutdownMonitor) {
			StartupStep contextRefresh = this.applicationStartup.start("context.refresh");
			prepareRefresh();
			ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
			prepareBeanFactory(beanFactory);
			try {
				StartupStep beanPostProcess = this.applicationStartup.start("context.beans.post-process");
				invokeBeanFactoryPostProcessors(beanFactory);
				registerBeanPostProcessors(beanFactory);
				beanPostProcess.end();
				StartupStep preInstantiateSingletons = this.applicationStartup.start("context.preInstantiateSingletons");
				finishBeanFactoryInitialization(beanFactory);
				preInstantiateSingletons.end();
			} catch (BeansException ex) {
				if (log.isWarnEnabled()) {
					log.warn("Exception encountered during context initialization - " + "cancelling refresh attempt: " + ex);
				}
				cancelRefresh(ex);
				throw ex;
			} finally {
				contextRefresh.end();
			}
		}
	}

	protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
		for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessors) {
			beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
		}
	}

	protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
		Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
		for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
			beanFactory.addBeanPostProcessor(beanPostProcessor);
		}
	}

	protected void cancelRefresh(BeansException ex) {
		this.active.set(false);
	}

	protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
		beanFactory.preInstantiateSingletons();
	}

	protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
		beanFactory.setBeanClassLoader(getClassLoader());
	}

	protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
		refreshBeanFactory();
		return getBeanFactory();
	}

	protected void prepareRefresh() {
		this.startupDate = System.currentTimeMillis();
		this.closed.set(false);
		this.active.set(true);

		if (log.isDebugEnabled()) {
			if (log.isTraceEnabled()) {
				log.trace("Refreshing " + this);
			} else {
				log.debug("Refreshing " + getDisplayName());
			}
		}
	}

	@Override
	public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

	@Override
	public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
		this.beanFactoryPostProcessors.add(postProcessor);
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	protected BeanFactory getInternalParentBeanFactory() {
		return (getParent() instanceof ConfigurableApplicationContext ?
				((ConfigurableApplicationContext) getParent()).getBeanFactory() : getParent());
	}

	@Override
	public String[] getBeanDefinitionNames() {
		return getBeanFactory().getBeanDefinitionNames();
	}

	public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
		return getBeanFactory().getBeansOfType(type);
	}

	public Object getBean(String name) throws BeansException {
		return getBeanFactory().getBean(name);
	}

	@Override
	public BeanFactory getParentBeanFactory() {
		return getParent();
	}

	@Override
	public ApplicationContext getParent() {
		return this.parent;
	}

	@Override
	public void setParent(ApplicationContext parent) {
		this.parent = parent;
	}

	protected abstract void refreshBeanFactory() throws BeansException, IllegalStateException;

	protected abstract void closeBeanFactory();
}
