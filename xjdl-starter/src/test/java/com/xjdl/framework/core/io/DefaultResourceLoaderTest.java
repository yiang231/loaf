package com.xjdl.framework.core.io;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.io.IOUtil;

import java.io.InputStream;
import java.util.Collection;

@Slf4j
class DefaultResourceLoaderTest {

	@Test
	void getResource() throws Exception {
		DefaultResourceLoader resourceLoader = new DefaultResourceLoader();

		Resource resource = resourceLoader.getResource("classpath:log4j2.xml");
		InputStream inputStream = resource.getInputStream();
		print(inputStream);

		inputStream.close();
	}

	private void print(InputStream inputStream) {
		Collection<String> content = IOUtil.readLines(inputStream);
		content.forEach(log::info);
	}
}
