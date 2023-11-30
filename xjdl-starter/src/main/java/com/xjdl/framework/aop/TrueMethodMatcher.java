package com.xjdl.framework.aop;

import java.io.Serializable;
import java.lang.reflect.Method;

final class TrueMethodMatcher implements MethodMatcher, Serializable {

    public static final TrueMethodMatcher INSTANCE = new TrueMethodMatcher();

    private TrueMethodMatcher() {
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object... args) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "MethodMatcher.TRUE";
    }

    private Object readResolve() {
        return INSTANCE;
    }
}