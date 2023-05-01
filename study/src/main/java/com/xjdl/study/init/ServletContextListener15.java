package com.xjdl.study.init;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 1、实现ServletContextListener接口
 * 在初始化Web应用程序中的任何过滤器或servlet之前，将通知所有servletContextListener上下文初始化。
 */
@Slf4j
@WebListener
public class ServletContextListener15 implements ServletContextListener {
	static {
		log.info("InitTest1.static initializer");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.info("ServletContextListener#InitTest15.contextInitialized");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("ServletContextListener#InitTest15.contextDestroyed");
	}
}

