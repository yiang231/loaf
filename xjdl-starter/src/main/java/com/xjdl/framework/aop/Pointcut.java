package com.xjdl.framework.aop;

public interface Pointcut {
    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();
}
