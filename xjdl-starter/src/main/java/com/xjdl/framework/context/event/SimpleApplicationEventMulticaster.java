package com.xjdl.framework.context.event;

import com.xjdl.framework.beans.factory.BeanFactory;
import com.xjdl.framework.context.ApplicationEvent;
import com.xjdl.framework.context.ApplicationListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 简单实现。TODO 后续补充
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {
	public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
		setBeanFactory(beanFactory);
	}

	public SimpleApplicationEventMulticaster() {
	}

	@Override
	public void multicastEvent(ApplicationEvent event) {
		for (ApplicationListener<ApplicationEvent> applicationListener : applicationListeners) {
			if (supportsEvent(applicationListener, event)) {
				applicationListener.onApplicationEvent(event);
			}
		}
	}

	/**
	 * 监听器是否对该事件感兴趣
	 */
	protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
		Type type = applicationListener.getClass().getGenericInterfaces()[0];
		Type actualTypeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
		String className = actualTypeArgument.getTypeName();
		Class<?> eventClassName;
		try {
			eventClassName = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("wrong event class name: " + className);
		}
		return eventClassName.isAssignableFrom(event.getClass());
	}
}
