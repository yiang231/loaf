package com.xjdl.framework.context.support;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.BeanFactory;
import com.xjdl.framework.beans.factory.config.AutowireCapableBeanFactory;
import com.xjdl.framework.beans.factory.config.BeanPostProcessor;
import com.xjdl.framework.beans.factory.config.ConfigurableListableBeanFactory;
import com.xjdl.framework.beans.factory.support.AbstractBeanFactory;
import com.xjdl.framework.beans.factory.support.BeanDefinitionRegistry;
import com.xjdl.framework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import com.xjdl.framework.beans.factory.support.BeanFactoryPostProcessor;
import com.xjdl.framework.context.ApplicationContext;
import com.xjdl.framework.context.ApplicationEvent;
import com.xjdl.framework.context.ApplicationListener;
import com.xjdl.framework.context.ConfigurableApplicationContext;
import com.xjdl.framework.context.event.ApplicationEventMulticaster;
import com.xjdl.framework.context.event.ContextClosedEvent;
import com.xjdl.framework.context.event.ContextRefreshedEvent;
import com.xjdl.framework.context.event.SimpleApplicationEventMulticaster;
import com.xjdl.framework.core.io.DefaultResourceLoader;
import com.xjdl.framework.core.io.Resource;
import com.xjdl.framework.core.io.support.ResourcePatternResolver;
import com.xjdl.framework.core.metrics.ApplicationStartup;
import com.xjdl.framework.core.metrics.StartupStep;
import com.xjdl.framework.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 面向使用者
 */
@Slf4j
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
	public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";
	private final AtomicBoolean active = new AtomicBoolean();
	private final AtomicBoolean closed = new AtomicBoolean();
	private final Object startupShutdownMonitor = new Object();
	private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();
	private ApplicationEventMulticaster applicationEventMulticaster;
	private ResourcePatternResolver resourcePatternResolver;
	private Thread shutdownHook;
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
		this.resourcePatternResolver = getResourcePatternResolver();
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
				initApplicationEventMulticaster();
				onRefresh();
				registerListeners();
				finishBeanFactoryInitialization(beanFactory);
				finishRefresh();
			} catch (BeansException ex) {
				if (log.isWarnEnabled()) {
					log.warn("Exception encountered during context initialization - " + "cancelling refresh attempt: " + ex);
				}
				destroyBeans();
				cancelRefresh(ex);
				throw ex;
			} finally {
				resetCommonCaches();
				contextRefresh.end();
			}
		}
	}

	protected void finishRefresh() {
		publishEvent(new ContextRefreshedEvent(this));
	}

	protected void initApplicationEventMulticaster() {
		ConfigurableListableBeanFactory beanFactory = getBeanFactory();
		this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
		beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, this.applicationEventMulticaster);
	}

	protected void onRefresh() throws BeansException {
		// 交由 WebApplicationContext 处理
	}

	protected void registerListeners() {
		Map<String, ApplicationListener> applicationListenerMap = getBeansOfType(ApplicationListener.class);
		List<ApplicationListener> applicationListeners = new ArrayList<>(applicationListenerMap.values());
		for (ApplicationListener applicationListener : applicationListeners) {
			getApplicationEventMulticaster().addApplicationListener(applicationListener);
		}
	}

	ApplicationEventMulticaster getApplicationEventMulticaster() {
		if (this.applicationEventMulticaster == null) {
			throw new IllegalStateException("ApplicationEventMulticaster not initialized - " + "call 'refresh' before multicasting events via the context: " + this);
		}
		return this.applicationEventMulticaster;
	}

	@Override
	public void publishEvent(Object event) {
		getApplicationEventMulticaster().multicastEvent((ApplicationEvent) event);
	}

	@Override
	public Resource[] getResources(String locationPattern) throws IOException {
		return this.resourcePatternResolver.getResources(locationPattern);
	}

	protected ResourcePatternResolver getResourcePatternResolver() {
		// TODO 后期实现
		return null;
	}

	protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
		Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessors = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
		for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessors.values()) {
			this.addBeanFactoryPostProcessor(beanFactoryPostProcessor);
		}
		invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors());
	}

	public void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory, List<BeanFactoryPostProcessor> beanFactoryPostProcessors) {
		if (beanFactory instanceof BeanDefinitionRegistry) {
			BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
			List<BeanFactoryPostProcessor> regularPostProcessors = new ArrayList<>();
			List<BeanDefinitionRegistryPostProcessor> registryProcessors = new ArrayList<>();

			for (BeanFactoryPostProcessor postProcessor : beanFactoryPostProcessors) {
				if (postProcessor instanceof BeanDefinitionRegistryPostProcessor) {
					BeanDefinitionRegistryPostProcessor registryProcessor =
							(BeanDefinitionRegistryPostProcessor) postProcessor;
					registryProcessor.postProcessBeanDefinitionRegistry(registry);
					registryProcessors.add(registryProcessor);
				} else {
					regularPostProcessors.add(postProcessor);
				}
			}

			invokeBeanFactoryPostProcessors(registryProcessors, beanFactory);
			invokeBeanFactoryPostProcessors(regularPostProcessors, beanFactory);
		}
	}

	private void invokeBeanFactoryPostProcessors(Collection<? extends BeanFactoryPostProcessor> postProcessors, ConfigurableListableBeanFactory beanFactory) {
		for (BeanFactoryPostProcessor postProcessor : postProcessors) {
			StartupStep postProcessBeanFactory = beanFactory.getApplicationStartup()
					.start("context.bean-factory.post-process")
					.tag("postProcessor", postProcessor::toString);
			postProcessor.postProcessBeanFactory(beanFactory);
			postProcessBeanFactory.end();
		}
	}

	protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
		Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
		List<BeanPostProcessor> beanPostProcessors = new ArrayList<>(beanPostProcessorMap.values());
		registerBeanPostProcessors(beanFactory, beanPostProcessors);
	}

	private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory, List<BeanPostProcessor> postProcessors) {
		if (beanFactory instanceof AbstractBeanFactory) {
			((AbstractBeanFactory) beanFactory).addBeanPostProcessors(postProcessors);
		} else {
			for (BeanPostProcessor postProcessor : postProcessors) {
				beanFactory.addBeanPostProcessor(postProcessor);
			}
		}
	}

	@Override
	public String[] getBeanNamesForType(Class<?> type) {
		return getBeanFactory().getBeanNamesForType(type);
	}

	/**
	 * 准备 BeanFactory ，并且配置各种属性
	 */
	protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
		beanFactory.setBeanClassLoader(getClassLoader());
		beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
		if (!beanFactory.containsLocalBean(APPLICATION_STARTUP_BEAN_NAME)) {
			beanFactory.registerSingleton(APPLICATION_STARTUP_BEAN_NAME, getApplicationStartup());
		}
	}

	public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors() {
		return this.beanFactoryPostProcessors;
	}

	@Override
	public ApplicationStartup getApplicationStartup() {
		return this.applicationStartup;
	}

	@Override
	public void close() {
		synchronized (this.startupShutdownMonitor) {
			doClose();
			// If we registered a JVM shutdown hook, we don't need it anymore now:
			// We've already explicitly closed the context.
			if (this.shutdownHook != null) {
				try {
					Runtime.getRuntime().removeShutdownHook(this.shutdownHook);
				} catch (IllegalStateException ex) {
					// ignore - VM is already shutting down
				}
			}
		}
	}

	@Override
	public boolean containsLocalBean(String name) {
		return getBeanFactory().containsLocalBean(name);
	}

	protected void doClose() {
		if (this.active.get() && this.closed.compareAndSet(false, true)) {
			if (log.isDebugEnabled()) {
				log.debug("Closing " + this);
			}
			try {
				publishEvent(new ContextClosedEvent(this));
			} catch (Throwable ex) {
				log.warn("Exception thrown from ApplicationListener handling ContextClosedEvent", ex);
			}
			destroyBeans();
			closeBeanFactory();
			onClose();
			resetCommonCaches();
			this.active.set(false);
		}
	}

	protected void resetCommonCaches() {
	}

	protected void onClose() {
	}

	protected void destroyBeans() {
		getBeanFactory().destroySingletons();
	}

	@Override
	public void registerShutdownHook() {
		if (this.shutdownHook == null) {
			// No shutdown hook registered yet.
			this.shutdownHook = new Thread(SHUTDOWN_HOOK_THREAD_NAME) {
				@Override
				public void run() {
					synchronized (startupShutdownMonitor) {
						doClose();
					}
				}
			};
			Runtime.getRuntime().addShutdownHook(this.shutdownHook);
		}
	}

	@Override
	public boolean isActive() {
		return this.active.get();
	}

	protected void cancelRefresh(BeansException ex) {
		this.active.set(false);
	}

	/**
	 * 预实例化对象
	 */
	protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
		beanFactory.preInstantiateSingletons();
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

	/**
	 * 创建 DefaultListableBeanFactory，并且扫描 XML 配置文件
	 */
	protected abstract void refreshBeanFactory() throws BeansException, IllegalStateException;

	protected abstract void closeBeanFactory();

	@Override
	public boolean containsBean(String name) {
		return getBeanFactory().containsBean(name);
	}
}
