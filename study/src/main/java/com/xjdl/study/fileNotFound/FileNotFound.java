package com.xjdl.study.fileNotFound;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@SpringBootTest
@Slf4j
public class FileNotFound {
	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * 基于spring
	 */
	@Test
	void test1() throws IOException {
		ClassPathResource classPathResource = new ClassPathResource("logback-test.xml");
		InputStream inputStream = classPathResource.getInputStream();
		charRead(inputStream);
	}

	/**
	 * 从根开始找文件
	 */
	@Test
	void test2() {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("logback-test.xml");
		charRead(inputStream);
	}

	/**
	 * 需要手动加/表示根路径
	 */
	@Test
	void test3() {
		InputStream inputStream = getClass().getResourceAsStream("/logback-test.xml");
		charRead(inputStream);
	}

	/**
	 * 同上不需要加/
	 */
	@Test
	void test4() {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("logback-test.xml");
		charRead(inputStream);
	}

	/**
	 * classpath:代表jar包根目录
	 * 不推荐
	 */
	@Test
	void test5() throws FileNotFoundException {
		File file = ResourceUtils.getFile("classpath:logback-test.xml");
		FileInputStream fileInputStream = new FileInputStream(file);
		charRead(fileInputStream);
	}

	/**
	 * 基于spring
	 */
	@Test
	void test6() throws IOException {
		Resource resource = resourceLoader.getResource("classpath:logback-test.xml");
		InputStream inputStream = resource.getInputStream();
		charRead(inputStream);
	}

	/**
	 * 基于spring
	 */
	@Test
	void test7() throws IOException {
		Resource resource = applicationContext.getResource("classpath:logback-test.xml");
		InputStream inputStream = resource.getInputStream();
		charRead(inputStream);
	}

	/**
	 * 资源路径获取
	 */
	@Test
	void testPath() {
		try {
			log.info("{}", getClass().getClassLoader().getResource("").getPath());
			log.info("中文路径下获取资源需要解码 {}", URLDecoder.decode(getClass().getClassLoader().getResource("logback-test.xml").getPath(), "UTF-8"));
			log.info("{}", getClass().getResourceAsStream("/logback-test.xml"));
			log.info("当前项目根的绝对路径 {}", System.getProperty("user.dir"));
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		}
	}

	void charRead(InputStream inputStream) {
		try (BufferedReader bufferedReader =
					 new BufferedReader(
							 new InputStreamReader(inputStream, StandardCharsets.UTF_8)
							 , 10 * 1024 * 1024)) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				log.info(line);
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	void byteRead(InputStream inputStream) {
		try {
			int length;
			byte[] bytes = new byte[8192];
			while ((length = inputStream.read(bytes)) != -1) {
				log.info(new String(bytes, 0, length));
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
