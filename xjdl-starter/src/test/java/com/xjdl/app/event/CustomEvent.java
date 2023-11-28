package com.xjdl.app.event;

import com.xjdl.framework.context.ApplicationContext;
import com.xjdl.framework.context.event.ApplicationContextEvent;

/**
 * 自定义事件进行发布，目前的发布时机是创建完容器之后
 */
public class CustomEvent extends ApplicationContextEvent {
	public CustomEvent(ApplicationContext source) {
		super(source);
	}
}
