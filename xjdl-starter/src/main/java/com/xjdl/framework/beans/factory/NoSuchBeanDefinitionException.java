package com.xjdl.framework.beans.factory;

import com.xjdl.framework.beans.BeansException;

public class NoSuchBeanDefinitionException extends BeansException {
	public NoSuchBeanDefinitionException(String msg) {
		super(msg);
	}

	public NoSuchBeanDefinitionException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
