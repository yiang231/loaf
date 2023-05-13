package com.xjdl.study.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * meta-annotated 注解元标签
 * Documented
 * Retention
 * Target
 * Inherited
 * Repeatable
 * Native 不常用
 */
@Documented // 将元素包含到javadoc中
@Retention(RetentionPolicy.RUNTIME) // 保留到运行时，会被加载进JVM
@Target({METHOD, TYPE, CONSTRUCTOR, FIELD}) // 运用场景
@Inherited // 继承注解，子类没有注解，则继承此注解
@Repeatable(PersonsAnnotation.class) // 可重复的注解，注解值不同
@Deprecated
public @interface PersonAnnotation {
    String name() default "dl";

    int age();
}

