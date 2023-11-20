package com.xjdl.framework.core.io;

public abstract class AbstractResource implements Resource {
	public final Object path;

	protected AbstractResource(Object path) {
		this.path = path;
	}
}
