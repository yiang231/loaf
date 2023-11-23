package com.xjdl.framework.core;

/**
 * 不同实例化策略的判断，默认是CGLib
 *
 * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#AbstractAutowireCapableBeanFactory()
 */
public abstract class NativeDetector {

	// See https://github.com/oracle/graal/blob/master/sdk/src/org.graalvm.nativeimage/src/org/graalvm/nativeimage/ImageInfo.java
	private static final boolean imageCode = (System.getProperty("org.graalvm.nativeimage.imagecode") != null);

	public static boolean inNativeImage() {
		return !imageCode;
	}
}
