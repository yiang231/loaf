package com.xjdl.framework.context.event;

import com.xjdl.framework.context.ApplicationContext;

public class ContextRefreshedEvent extends ApplicationContextEvent {
	public ContextRefreshedEvent(ApplicationContext source) {
		super(source);
	}
}
