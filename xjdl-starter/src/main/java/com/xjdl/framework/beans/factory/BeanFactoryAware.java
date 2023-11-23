package com.xjdl.framework.beans.factory;

import com.xjdl.framework.beans.BeansException;

public interface BeanFactoryAware extends Aware {
	void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
