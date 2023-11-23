package com.xjdl.framework.context;

import com.xjdl.framework.beans.factory.HierarchicalBeanFactory;
import com.xjdl.framework.beans.factory.ListableBeanFactory;
import com.xjdl.framework.beans.factory.config.AutowireCapableBeanFactory;
import com.xjdl.framework.core.io.ResourceLoader;

/**
 * 顶级接口，用于获取各种信息
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader {
	/**
	 * 子接口的同一实现
	 *
	 * @see com.xjdl.framework.context.ConfigurableApplicationContext#getBeanFactory()
	 */
	AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException;

	ApplicationContext getParent();

	String getId();

	String getDisplayName();
}
