package com.xjdl.framework.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class UrlByInputStreamResource implements Resource {

	private final URL url;

	public UrlByInputStreamResource(URL url) {
		this.url = url;
	}

	/**
	 * 返回的是输入流，用完记得关闭
	 */
	@Override
	public InputStream getInputStream() throws IOException {
		URLConnection urlConnection = url.openConnection();
		urlConnection.connect();
		return urlConnection.getInputStream();
	}
}
