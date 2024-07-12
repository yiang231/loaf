package com.xjdl.framework.beans.factory;

import com.xjdl.framework.beans.BeansException;

@FunctionalInterface
public interface ObjectFactory<T> {
	T getObject() throws BeansException;
}
