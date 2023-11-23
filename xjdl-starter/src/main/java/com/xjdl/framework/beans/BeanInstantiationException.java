package com.xjdl.framework.beans;

public class BeanInstantiationException extends BeansException {
	public BeanInstantiationException(String msg) {
		super(msg);
	}

	public BeanInstantiationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
