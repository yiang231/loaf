package com.xjdl.study.javaWeb3.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.annotation.WebListener;

@Slf4j
@WebListener
public class ServletRequestAttributeListenerDemo implements ServletRequestAttributeListener {
	@Override
	public void attributeAdded(ServletRequestAttributeEvent servletRequestAttributeEvent) {

	}

	@Override
	public void attributeRemoved(ServletRequestAttributeEvent servletRequestAttributeEvent) {

	}

	@Override
	public void attributeReplaced(ServletRequestAttributeEvent servletRequestAttributeEvent) {

	}
}
