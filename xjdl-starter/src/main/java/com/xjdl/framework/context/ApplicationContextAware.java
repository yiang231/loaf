package com.xjdl.framework.context;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.Aware;

/**
 * ApplicationContext 感知接口
 */
public interface ApplicationContextAware extends Aware {
	void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
