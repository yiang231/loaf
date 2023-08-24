package com.xjdl.juc.lambda;

/**
 * jdk8以后,接口作用是允许有方法实现的,支持 静态和默认方法的实现
 * 用 @FunctionalInterface 注解修饰过的接口是一个函数式接口
 */
//@FunctionalInterface
public interface Foo {
	public static int add(int a, int b, int c) {
		return a + b + c;
	}
	//public int add1(int a,int b);

	public int add(int a, int b);

	;

	public default int add(int a, int b, int c, int d) {
		return a + b + c + d;
	}

	;
}
