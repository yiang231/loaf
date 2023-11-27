package com.xjdl.framework.beans.factory.support;

import java.security.AccessControlContext;
import java.security.AccessController;

public class SimpleSecurityContextProvider implements SecurityContextProvider {
	private final AccessControlContext acc;

	public SimpleSecurityContextProvider() {
		this(null);
	}

	public SimpleSecurityContextProvider(AccessControlContext acc) {
		this.acc = acc;
	}

	public AccessControlContext getAccessControlContext() {
		return (this.acc != null ? this.acc : AccessController.getContext());
	}
}
