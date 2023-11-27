package com.xjdl.framework.context;

import java.util.EventListener;

/**
 * 监听器监听事件
 */
@FunctionalInterface
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
	void onApplicationEvent(E event);
}
