package com.xjdl.app.config;

import com.xjdl.app.service.BaseService;
import com.xjdl.framework.beans.factory.config.BeanPostProcessor;
import com.xjdl.framework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
public class XjdlBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialize(String name, Object bean) {
        System.out.println(name + " .postProcessBeforeInitialize");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialize(String name, Object bean) {
        System.out.println(name + " .postProcessAfterInitialize");
        if (bean instanceof BaseService) {
            Object instance = Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{BaseService.class}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println(name + " invoke proxy method");
                    return method.invoke(bean, args);
                }
            });
            return instance;
        }
        return bean;
    }
}
