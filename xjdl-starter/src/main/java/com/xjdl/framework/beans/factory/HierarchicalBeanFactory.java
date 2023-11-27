package com.xjdl.framework.beans.factory;

/**
 * AbstractApplicationContext 和 AbstractBeanFactory 建立关系的桥梁
 */
public interface HierarchicalBeanFactory extends BeanFactory {
	BeanFactory getParentBeanFactory();

	boolean containsLocalBean(String name);
}
