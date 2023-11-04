package com.xjdl.framework.context;


import com.xjdl.framework.beans.factory.Aware;

public interface ApplicationContextAware extends Aware {
	void setApplicationContext(ApplicationContext applicationContext);

}
