package com.xjdl.framework.context;

import java.time.Clock;
import java.util.EventObject;

public abstract class ApplicationEvent extends EventObject {
	private final long timestamp;

	public ApplicationEvent(Object source) {
		super(source);
		this.timestamp = System.currentTimeMillis();
	}

	public ApplicationEvent(Object source, Clock clock) {
		super(source);
		this.timestamp = clock.millis();
	}

	public final long getTimestamp() {
		return this.timestamp;
	}
}
