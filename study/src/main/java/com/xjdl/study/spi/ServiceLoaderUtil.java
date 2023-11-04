package com.xjdl.study.spi;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

/**
 * Hutool 提供的基于原生的 SPI 实现
 */
public class ServiceLoaderUtil {
	public ServiceLoaderUtil() {

	}

	public static <T> T loadFirstAvailable(Class<T> clazz) {
		Iterator<T> iterator = load(clazz).iterator();
		while (iterator.hasNext()) {
			try {
				return iterator.next();
			} catch (ServiceConfigurationError var3) {

			}
		}
		return null;
	}

	public static <T> T loadFirst(Class<T> clazz) {
		Iterator<T> iterator = load(clazz).iterator();
		return iterator.hasNext() ? iterator.next() : null;
	}

	public static <T> ServiceLoader<T> load(Class<T> clazz) {
		return ServiceLoader.load(clazz);
	}

	public static <T> ServiceLoader<T> load(Class<T> clazz, ClassLoader loader) {
		return ServiceLoader.load(clazz, loader);
	}
}
