package com.xjdl.framework.core.io;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystemResource extends AbstractResource {
	public FileSystemResource(Object path) {
		super(path);
	}

	@Override
	public InputStream getInputStream() throws Exception {
		Path path = new File((String) this.path).toPath();
		return Files.newInputStream(path);
	}
}
