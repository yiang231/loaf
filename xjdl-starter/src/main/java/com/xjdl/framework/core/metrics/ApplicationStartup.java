package com.xjdl.framework.core.metrics;

/**
 * 容器启动状态和步骤标识
 */
public interface ApplicationStartup {
	ApplicationStartup DEFAULT = new DefaultApplicationStartup();

	StartupStep start(String name);
}
