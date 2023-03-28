package com.dl.test.javaWeb3.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * ServletRequest生命周期
 * 创建
 * 每一次的请求
 * 销毁
 * 请求结束
 * <p>
 * 执行新请求前，老请求会被销毁，执行完业务后会再创建新的请求
 */
@WebListener
public class ServletRequestListenerDemo implements ServletRequestListener {
    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        System.out.println("ServletRequestListenerDemo 被创建" + servletRequestEvent);
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        System.out.println("ServletRequestListenerDemo 被销毁" + servletRequestEvent);
    }
}
