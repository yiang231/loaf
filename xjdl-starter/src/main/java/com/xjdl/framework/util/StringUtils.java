package com.xjdl.framework.util;

import java.util.Collection;

public abstract class StringUtils {
	private static final String[] EMPTY_STRING_ARRAY = {};

	public static boolean hasLength(String str) {
		return (str != null && !str.isEmpty());
	}

	public static boolean hasText(String str) {
		return (str != null && !str.isEmpty() && containsText(str));
	}

	private static boolean containsText(CharSequence str) {
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static String[] toStringArray(Collection<String> collection) {
		return (!CollectionUtils.isEmpty(collection) ? collection.toArray(EMPTY_STRING_ARRAY) : EMPTY_STRING_ARRAY);
	}
}
