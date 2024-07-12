package com.xjdl.framework.aop;

@FunctionalInterface
public interface ClassFilter {
    boolean matches(Class<?> clazz);
}
