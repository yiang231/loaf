package com.xjdl.study.spi;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Documented // 将元素包含到javadoc中
@Retention(RetentionPolicy.RUNTIME) // 保留到运行时，会被加载进JVM
@Target({TYPE}) // 运用场景
@Inherited // 继承注解，子类没有注解，则继承此注解
public @interface XJDL {
}
