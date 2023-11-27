package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.BeansException;

public class BeanDefinitionValidationException extends BeansException {
	public BeanDefinitionValidationException(String msg) {
		super(msg);
	}

	public BeanDefinitionValidationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
