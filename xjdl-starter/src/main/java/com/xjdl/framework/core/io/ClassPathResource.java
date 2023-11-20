package com.xjdl.framework.core.io;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ClassPathResource extends AbstractResource {
	protected ClassPathResource(Object path) {
		super(path);
	}

	@Override
	public InputStream getInputStream() throws Exception {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream((String) this.path);
		if (is == null) {
			throw new FileNotFoundException(this.path + " cannot be opened because it does not exist");
		}
		return is;
	}
}
