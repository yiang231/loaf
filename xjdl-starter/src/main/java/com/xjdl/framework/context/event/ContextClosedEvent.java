package com.xjdl.framework.context.event;

import com.xjdl.framework.context.ApplicationContext;

public class ContextClosedEvent extends ApplicationContextEvent {
	public ContextClosedEvent(ApplicationContext source) {
		super(source);
	}
}
