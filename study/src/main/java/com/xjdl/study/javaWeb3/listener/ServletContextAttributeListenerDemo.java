package com.xjdl.study.javaWeb3.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

@Slf4j
@WebListener
public class ServletContextAttributeListenerDemo implements ServletContextAttributeListener {
	@Override
	public void attributeAdded(ServletContextAttributeEvent servletContextAttributeEvent) {
		log.info("ServletContextAttributeListenerDemo 添加的属性 {}, {}", servletContextAttributeEvent.getName(), servletContextAttributeEvent.getValue());
	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent servletContextAttributeEvent) {
		log.info("ServletContextAttributeListenerDemo 移除的属性 {}, {}", servletContextAttributeEvent.getName(), servletContextAttributeEvent.getValue());
	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent servletContextAttributeEvent) {
		log.info("ServletContextAttributeListenerDemo 修改前的属性 {}, {}", servletContextAttributeEvent.getName(), servletContextAttributeEvent.getValue());
	}
}
