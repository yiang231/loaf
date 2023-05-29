package com.xjdl.study.aspect.proxy.jdk;

/**
 * jdk动态代理
 * 必须实现接口
 */
public interface Subject {
	/**
	 * 无参调用
	 */
	void req(String value);

	/**
	 * 有参调用
	 */
	String resp();
}
