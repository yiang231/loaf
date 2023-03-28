package com.dl.test.javaWeb3.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * JavaBean的状态改变
 * <p>
 * 绑定
 * 对象被添加进session中
 * 解绑
 * 对象从session中移除
 */
@WebListener
public class HttpSessionBindingListenerDemo implements HttpSessionBindingListener {
    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("HttpSessionBindingListenerDemo 被绑定");
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("HttpSessionBindingListenerDemo 被解绑");
    }
}
