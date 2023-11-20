package com.xjdl.framework.core.io;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class UrlResource extends AbstractResource {
	protected UrlResource(Object path) {
		super(path);
	}

	/**
	 * 返回的是输入流，用完记得关闭
	 */
	@Override
	public InputStream getInputStream() throws Exception {
		URLConnection urlConnection = ((URL) path).openConnection();
		urlConnection.connect();
		return urlConnection.getInputStream();
	}
}
