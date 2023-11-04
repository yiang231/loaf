package com.xjdl.framework.beans.factory;

public interface BeanFactoryAware extends Aware {
	void setBeanFactory(ComponentFactory beanFactory);
}
