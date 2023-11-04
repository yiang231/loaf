package com.xjdl.framework.beans.factory.support;

public interface BeanNameGenerator {
	<T> String generate(Class<T> beanClass);
}
