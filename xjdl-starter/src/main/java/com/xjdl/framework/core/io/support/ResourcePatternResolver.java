package com.xjdl.framework.core.io.support;

import com.xjdl.framework.core.io.Resource;
import com.xjdl.framework.core.io.ResourceLoader;

import java.io.IOException;

public interface ResourcePatternResolver extends ResourceLoader {
	String CLASSPATH_ALL_URL_PREFIX = "classpath*:";

	Resource[] getResources(String locationPattern) throws IOException;
}
