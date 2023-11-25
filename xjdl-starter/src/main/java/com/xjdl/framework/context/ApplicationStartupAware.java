package com.xjdl.framework.context;

import com.xjdl.framework.beans.factory.Aware;
import com.xjdl.framework.core.metrics.ApplicationStartup;

public interface ApplicationStartupAware extends Aware {
	void setApplicationStartup(ApplicationStartup applicationStartup);
}
