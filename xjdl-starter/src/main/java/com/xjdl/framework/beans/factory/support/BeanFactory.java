package com.xjdl.framework.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单的 Bean 容器
 */
public class BeanFactory {
    private final Map<String, Object> beanMap = new ConcurrentHashMap<>();

    public void registerBean(String name, Object bean) {
        beanMap.put(name, bean);
    }

    public Map<String, Object> getBeanMap() {
        return beanMap;
    }

    public Object getBean(String name) {
        return beanMap.get(name);
    }
}
