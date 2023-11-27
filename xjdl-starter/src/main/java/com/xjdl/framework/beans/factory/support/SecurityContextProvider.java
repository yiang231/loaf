package com.xjdl.framework.beans.factory.support;

import java.security.AccessControlContext;

public interface SecurityContextProvider {
	AccessControlContext getAccessControlContext();
}
