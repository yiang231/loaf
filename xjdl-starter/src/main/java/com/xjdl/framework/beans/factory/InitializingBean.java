package com.xjdl.framework.beans.factory;

/**
 * 初始化 bean 的方法
 */
public interface InitializingBean {
	void afterPropertiesSet() throws Exception;
}
