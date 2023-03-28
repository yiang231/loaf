package com.dl.test.javaWeb3.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 用来初始化对象，初始化数据
 * 是对ServletContext对象的生命周期监控
 * <p>
 * 1. 加载数据库驱动
 * <p>
 * 2. 初始化连接池
 * <p>
 * 3. 定时器任务
 */
@WebListener
public class ServletContextListenerDemo implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("ServletContextListenerDemo 被初始化");

        ServletContext servletContext = servletContextEvent.getServletContext();
        System.out.println("servletContext = " + servletContext);

        ServletContext source = (ServletContext) servletContextEvent.getSource();
        System.out.println("source = " + source);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("ServletContextListenerDemo 被销毁");
    }
}
