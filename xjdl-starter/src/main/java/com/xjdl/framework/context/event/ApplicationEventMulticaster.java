package com.xjdl.framework.context.event;

import com.xjdl.framework.context.ApplicationEvent;
import com.xjdl.framework.context.ApplicationListener;

/**
 * 组织监听器触发事件
 */
public interface ApplicationEventMulticaster {
	void addApplicationListener(ApplicationListener<?> listener);

	void removeApplicationListener(ApplicationListener<?> listener);

	void multicastEvent(ApplicationEvent event);
}
