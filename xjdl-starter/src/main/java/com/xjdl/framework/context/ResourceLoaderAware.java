package com.xjdl.framework.context;

import com.xjdl.framework.beans.factory.Aware;
import com.xjdl.framework.core.io.ResourceLoader;

public interface ResourceLoaderAware extends Aware {
	void setResourceLoader(ResourceLoader resourceLoader);
}
