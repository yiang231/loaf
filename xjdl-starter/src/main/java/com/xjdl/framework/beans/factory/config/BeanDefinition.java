package com.xjdl.framework.beans.factory.config;

// interface

/**
 * 内部类的问题
 */
public class BeanDefinition {
    private String scope;
    private Class<?> type;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
