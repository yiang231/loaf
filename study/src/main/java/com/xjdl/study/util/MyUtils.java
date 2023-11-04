package com.xjdl.study.util;

import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
public abstract class MyUtils {
	/**
	 * 判断是否为正整数的正则表达式
	 */
	private static final String IS_NUMBER_REGEX = "^[1-9]\\d*$";

	/**
	 * 获取SpringBoot项目资源路径
	 *
	 * @param path 复制文件路径
	 * @return 获取资源路径
	 */
	public static String getResourcePath(String path) {
		String result = null;
		try {
			result = MyUtils.class.getClassLoader().getResource(path).getPath();
		} catch (NullPointerException e) {
			log.error("{} file not found !", path);
		}
		return result;
	}

	/**
	 * 获取当前线程
	 *
	 * @return 当前线程名称
	 */
	public static String getThreadName() {
		String threadName = Thread.currentThread().getName();
		if (log.isDebugEnabled()) {
			log.debug("当前线程是 {}", threadName);
		}
		return threadName;
	}

	/**
	 * 设置当前线程名称
	 *
	 * @param name 线程名称
	 */
	public static void setThreadName(String name) {
		Thread.currentThread().setName(name);
	}

	/**
	 * 判断是否为正整数
	 *
	 * @param text 要判断的字符
	 * @return 判断结果
	 */
	public static boolean isNumber(String text) {
		// text 完全匹配
		boolean result = Pattern.compile(IS_NUMBER_REGEX).matcher(text).matches();
		if (log.isDebugEnabled()) {
			log.debug("{} is{} a number", text, result ? "" : " not");
		}
		return result;
	}

	/**
	 * 获取当前线程上下文的类加载器
	 */
	public static ClassLoader getContextClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	/**
	 * 对象不为空
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	/**
	 * 对象为空
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}

		if (obj instanceof Optional) {
			return !((Optional<?>) obj).isPresent();
		}
		if (obj instanceof CharSequence) {
			return ((CharSequence) obj).length() == 0;
		}
		if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
		}
		if (obj instanceof Collection) {
			return ((Collection<?>) obj).isEmpty();
		}
		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).isEmpty();
		}

		// else
		return false;
	}

	public static <T> T mapToObj(Map map, Class<T> clazz) throws Exception {
		T obj = clazz.newInstance();
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		if (propertyDescriptors != null) {
			for (PropertyDescriptor property : propertyDescriptors) {
				Method setter = property.getWriteMethod();
				if (setter != null) {
					setter.invoke(obj, map.get(property.getName()));
				}
			}
		}
		return obj;
	}

	public static <T> Map<String, String> objTOMap(T obj) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		BeanInfo e = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propertyDescriptors = e.getPropertyDescriptors();
		if (propertyDescriptors != null) {
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				if (propertyDescriptor != null) {
					String name = propertyDescriptor.getName();
					if (name.equalsIgnoreCase("class")) {
						continue;
					}
					// TODO 注意：getXXX方法会被调用
					Method readMethod = propertyDescriptor.getReadMethod();
					String value = readMethod != null ? (String) readMethod.invoke(obj) : null;
					map.put(name, value);
				}
			}
		}
		return map;
	}

	/**
	 * 字符串判空
	 */
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
}
