package com.dl.test.javaWeb3.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletContextAttributeListenerDemo implements ServletContextAttributeListener {
    @Override
    public void attributeAdded(ServletContextAttributeEvent servletContextAttributeEvent) {
        System.out.println("ServletContextAttributeListenerDemo 添加的属性" + servletContextAttributeEvent.getName() + " = " + servletContextAttributeEvent.getValue());
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent servletContextAttributeEvent) {
        System.out.println("ServletContextAttributeListenerDemo 移除的属性" + servletContextAttributeEvent.getName() + " = " + servletContextAttributeEvent.getValue());
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent servletContextAttributeEvent) {
        System.out.println("ServletContextAttributeListenerDemo 修改前的属性" + servletContextAttributeEvent.getName() + " = " + servletContextAttributeEvent.getValue());
    }
}
