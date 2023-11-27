package com.xjdl.framework.context;

@FunctionalInterface
public interface ApplicationEventPublisher {
	void publishEvent(Object event);
}
