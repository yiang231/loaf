package com.xjdl.framework.core.io;

import java.net.URL;

public class ResourceLoader {
	/**
	 * 加载单配置文件以输入流的方式，注意不同环境下地址的填写
	 */
	public Resource getUrlInputStreamResource(String location) {
		return new UrlByInputStreamResource(this.getUrl(location));
	}

	public URL getUrl(String location) {
		return this.getContextClassLoader().getResource(location);
	}

	public ClassLoader getContextClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
}
