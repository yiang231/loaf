package com.xjdl.framework.beans.factory;

import com.xjdl.framework.beans.BeansException;

public class BeanCreationException extends BeansException {
	public BeanCreationException(String msg) {
		super(msg);
	}

	public BeanCreationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
