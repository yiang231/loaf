package com.xjdl.framework.util;

import java.util.Collection;

public abstract class CollectionUtils {
	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}
}
