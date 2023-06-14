package com.xjdl.study.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

@Slf4j
public abstract class MyUtils {
	/**
	 * 判断是否为正整数的正则表达式
	 */
	public static final String REGEX = "^[1-9]\\d*$";

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
			log.error("{} 路径对应的文件不存在", path);
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
	 * 判断是否为正整数
	 *
	 * @param text 要判断的字符
	 * @return 判断结果
	 */
	public static boolean isNumber(String text) {
		boolean result = Pattern.compile(REGEX).matcher(text).matches();
		if (log.isDebugEnabled()) {
			log.debug("{} is{} a number", text, result ? "" : " not");
		}
		return result;
	}
}
