package com.xjdl.framework.beans;

public class BeanWrapper {
	private Object wrappedInstance;

	public BeanWrapper(Object wrappedInstance) {
		this.wrappedInstance = wrappedInstance;
	}

	public Object getWrappedInstance() {
		return wrappedInstance;
	}

	public void setWrappedInstance(Object wrappedInstance) {
		this.wrappedInstance = wrappedInstance;
	}

}

