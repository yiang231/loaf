package com.xjdl.study.javaWeb3.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;
import java.io.Serializable;

/**
 * JavaBean的状态改变
 * 钝化 序列化到硬盘
 * 活化 反序列化
 */
@Slf4j
@WebListener
public class HttpSessionActivationListenerDemo implements HttpSessionActivationListener, Serializable {
    private String name;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void sessionWillPassivate(HttpSessionEvent httpSessionEvent) {
        log.info("HttpSessionActivationListenerDemo 被钝化");
    }

    @Override
    public void sessionDidActivate(HttpSessionEvent httpSessionEvent) {
        log.info("HttpSessionActivationListenerDemo 被活化");
    }
}
