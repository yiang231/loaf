package com.xjdl.framework.aop;

public interface Pointcut {
    /**
     * 单例模式实践
     */
    Pointcut TRUE = TruePointcut.INSTANCE;

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();
}