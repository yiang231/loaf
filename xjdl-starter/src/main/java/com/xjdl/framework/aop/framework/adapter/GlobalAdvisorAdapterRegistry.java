package com.xjdl.framework.aop.framework.adapter;

public final class GlobalAdvisorAdapterRegistry {
	private GlobalAdvisorAdapterRegistry() {
	}

	private static AdvisorAdapterRegistry instance = new DefaultAdvisorAdapterRegistry();

	public static AdvisorAdapterRegistry getInstance() {
		return instance;
	}

	static void reset() {
		instance = new DefaultAdvisorAdapterRegistry();
	}
}
