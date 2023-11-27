package com.xjdl.framework.context.event;

import com.xjdl.framework.context.ApplicationContext;
import com.xjdl.framework.context.ApplicationEvent;

/**
 * 四大事件
 */
public abstract class ApplicationContextEvent extends ApplicationEvent {
	public ApplicationContextEvent(ApplicationContext source) {
		super(source);
	}

	public final ApplicationContext getApplicationContext() {
		return (ApplicationContext) getSource();
	}
}
