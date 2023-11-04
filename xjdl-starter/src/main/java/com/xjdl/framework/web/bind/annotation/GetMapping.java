package com.xjdl.framework.web.bind.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置类级别的访问路径
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@RequestMapping(method = RequestMethod.GET)
public @interface GetMapping {
	String value();
}
