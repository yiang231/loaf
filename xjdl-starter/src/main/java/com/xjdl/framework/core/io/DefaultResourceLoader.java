package com.xjdl.framework.core.io;

import lombok.extern.slf4j.Slf4j;

import java.net.URL;

@Slf4j
public class DefaultResourceLoader implements ResourceLoader {
	/**
	 * 加载单配置文件以输入流的方式，注意不同环境下地址的填写
	 */
	public URL getUrl(String location) {
		return this.getClassLoader().getResource(location);
	}

	@Override
	public ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	@Override
	public Resource getResource(String location) {
		if (location.startsWith(CLASSPATH_URL_PREFIX)) {
			// 类路径资源
			return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
		}
		try {
			//尝试当成url来处理
			URL url = new URL(location);
			return new UrlResource(url);
		} catch (Exception ex) {
			log.warn("The {} is treated as an external file.", location);
			// 当成文件系统下的资源处理
			return new FileSystemResource(location);
		}
	}
}
