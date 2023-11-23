package com.xjdl.framework.context;

import com.xjdl.framework.beans.BeansException;

public class ApplicationContextException extends BeansException {
	public ApplicationContextException(String msg) {
		super(msg);
	}

	public ApplicationContextException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
