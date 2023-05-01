package com.xjdl.study.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class MyUtils {
	/**
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
}
