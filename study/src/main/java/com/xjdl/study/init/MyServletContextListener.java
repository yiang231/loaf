package com.xjdl.study.init;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 实现ServletContextListener接口
 * 在初始化Web应用程序中的任何过滤器或servlet之前，将通知所有servletContextListener上下文初始化。
 */
@Slf4j
@WebListener
public class MyServletContextListener implements ServletContextListener {
	static {
		log.info("com.xjdl.study.init.MyServletContextListener Static block code");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.info("com.xjdl.study.init.MyServletContextListener.contextInitialized");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("com.xjdl.study.init.MyServletContextListener.contextDestroyed");
	}
}

