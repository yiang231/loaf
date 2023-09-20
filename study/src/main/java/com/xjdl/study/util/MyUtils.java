package com.xjdl.study.util;

import lombok.extern.slf4j.Slf4j;

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
}
