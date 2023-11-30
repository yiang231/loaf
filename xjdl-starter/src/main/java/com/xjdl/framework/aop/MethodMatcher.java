package com.xjdl.framework.aop;

import java.lang.reflect.Method;

public interface MethodMatcher {
    MethodMatcher TRUE = TrueMethodMatcher.INSTANCE;

    boolean matches(Method method, Class<?> targetClass, Object... args);
}