package com.xjdl.framework.beans.factory;

import com.xjdl.framework.beans.BeansException;

public class BeanDefinitionStoreException extends BeansException {

	public BeanDefinitionStoreException(String msg) {
		super(msg);
	}

	public BeanDefinitionStoreException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
