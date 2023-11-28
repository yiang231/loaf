package com.xjdl.framework.context.event;

import com.xjdl.framework.beans.factory.BeanClassLoaderAware;
import com.xjdl.framework.beans.factory.BeanFactory;
import com.xjdl.framework.beans.factory.BeanFactoryAware;
import com.xjdl.framework.beans.factory.config.ConfigurableBeanFactory;
import com.xjdl.framework.context.ApplicationEvent;
import com.xjdl.framework.context.ApplicationListener;
import com.xjdl.framework.util.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanClassLoaderAware, BeanFactoryAware {
	public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();
	private ClassLoader beanClassLoader;
	private ConfigurableBeanFactory beanFactory;

	@Override
	@SuppressWarnings({"unchecked"})
	public void addApplicationListener(ApplicationListener<?> listener) {
		synchronized (this.applicationListeners) {
			this.applicationListeners.remove(listener);
			this.applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
		}
	}

	@Override
	public void removeApplicationListener(ApplicationListener<?> listener) {
		synchronized (this.applicationListeners) {
			this.applicationListeners.remove(listener);
		}
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.beanClassLoader = classLoader;
	}

	private ConfigurableBeanFactory getBeanFactory() {
		if (this.beanFactory == null) {
			throw new IllegalStateException("ApplicationEventMulticaster cannot retrieve listener beans " +
					"because it is not associated with a BeanFactory");
		}
		return this.beanFactory;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		if (!(beanFactory instanceof ConfigurableBeanFactory)) {
			throw new IllegalStateException("Not running in a ConfigurableBeanFactory: " + beanFactory);
		}
		this.beanFactory = (ConfigurableBeanFactory) beanFactory;
		if (this.beanClassLoader == null) {
			this.beanClassLoader = this.beanFactory.getBeanClassLoader();
		}
	}

	/**
	 * 传入事件类型，找出对此事件感兴趣的所有的 Listener
	 */
	public Set<ApplicationListener<?>> getApplicationListeners(ApplicationEvent event) {
		Set<ApplicationListener<?>> allListeners = new LinkedHashSet<>();
		for (ApplicationListener<ApplicationEvent> applicationListener : applicationListeners) {
			if (supportsEvent(applicationListener, event)) {
				 allListeners.add(applicationListener);
			}
		}
		return allListeners;
	}

	/**
	 * 简单实现：直接获取 ApplicationListener 泛型中的 ApplicationEvent 事件类型
	 *
	 * @see org.springframework.context.event.AbstractApplicationEventMulticaster#retrieveApplicationListeners(org.springframework.core.ResolvableType, Class, org.springframework.context.event.AbstractApplicationEventMulticaster.CachedListenerRetriever)
	 */
	protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
		Type type = applicationListener.getClass().getGenericInterfaces()[0];
		Type actualTypeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
		String className = actualTypeArgument.getTypeName();
		Class<?> eventClassName;
		try {
			eventClassName = ClassUtils.getDefaultClassLoader().loadClass(className);
			return eventClassName.isAssignableFrom(event.getClass());
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Wrong event class name with " + className);
		}
	}
}
