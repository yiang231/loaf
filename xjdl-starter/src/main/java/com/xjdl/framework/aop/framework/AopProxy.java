package com.xjdl.framework.aop.framework;

public interface AopProxy {
    Object getProxy();

    Object getProxy(ClassLoader classLoader);
}
