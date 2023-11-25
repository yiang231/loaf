package com.xjdl.framework.beans.factory;

/**
 * 具有销毁方法的 Bean 接口
 */
public interface DisposableBean {
	void destroy() throws Exception;
}
