package com.xjdl.framework.beans;

public abstract class BeansException extends RuntimeException {
	public BeansException(String msg) {
		super(msg);
	}

	public BeansException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
