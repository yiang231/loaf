package com.xjdl.study.aspect.dLAspect;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * 自定义的日志切面注解
 */
@Retention(RetentionPolicy.RUNTIME) // 保留到运行时，会被加载进JVM
@Target({METHOD}) // 运用场景
public @interface DL {
}
