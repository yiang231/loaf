package com.xjdl.framework.context.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 目前是当前 controller 的扫描根路径，【请规范包名填写】
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface ComponentScan {
	String value();

	boolean required() default true;

	/**
	 * 默认是递归扫描
	 */
	boolean recursiveCall() default true;

	/**
	 * 路径下的组件懒加载初始化
	 * <p>
	 * TODO 需要重新调整 HandlerMapping 以及 HandlerAdapter 的元素添加时机。
	 */
	boolean lazyInit() default false;
}
