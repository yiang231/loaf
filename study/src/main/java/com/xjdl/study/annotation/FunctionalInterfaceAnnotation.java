package com.xjdl.study.annotation;

/**
 * 函数式接口
 * 用@FunctionalInterface注解声明
 * 只有一个方法的接口
 */
@FunctionalInterface
public interface FunctionalInterfaceAnnotation {
    static String staticMethod() {
        return "@FunctionalInterface 允许存在静态方法";
    }

    public abstract void run();

    default String defaultMethod() {
        return "@FunctionalInterface 允许存在默认方法";
    }
}
