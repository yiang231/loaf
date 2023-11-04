package com.xjdl.framework.context;


import com.xjdl.framework.beans.factory.ComponentFactory;

public interface ApplicationContext extends ComponentFactory {
	/**
	 * 模板方法
	 */
	void refresh() throws Exception;
}

