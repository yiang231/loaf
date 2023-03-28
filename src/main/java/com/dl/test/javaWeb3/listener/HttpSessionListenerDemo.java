package com.dl.test.javaWeb3.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 可以用来统计网站访问的人数
 * 设置 session = "true"
 * <p>
 * HttpSession对象生命周期
 * Session有ID
 * 创建
 * 第一次调用request.getSession()时被创建
 * 销毁
 * 服务器关闭 session过期 手动销毁
 */
@WebListener
public class HttpSessionListenerDemo implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        System.out.println("HttpSessionListenerDemo 被创建" + httpSessionEvent.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        System.out.println("HttpSessionListenerDemo 被销毁" + httpSessionEvent.getSession().getId());
    }
}
