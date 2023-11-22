package com.xjdl.framework.beans.factory;

public interface BeanClassLoaderAware extends Aware {
	void setBeanClassLoader(ClassLoader classLoader);
}
