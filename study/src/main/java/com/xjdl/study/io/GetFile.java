package com.xjdl.study.io;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @see File
 */
@Slf4j
public class GetFile {

	public static final String PROPERTY = System.getProperty("user.dir");
	public static final File FILE = new File(System.getProperty("user.dir"));

	@Test
	void getFile() {
//		File.createTempFile();//指定前后缀创建文件

		List<File> fileNames = getFileNames(PROPERTY);
		log.info("总计 {} 个文件", fileNames.size());
		fileNames.stream().parallel().forEachOrdered(file -> log.info("{}", file));
	}

	/**
	 * listFiles() 返回路径下内容信息，以 FIle.class
	 */
	@Test
	void listFiles() {
		for (File item : FILE.listFiles()) {
			log.info("文件信息为 {}", item);
		}
	}

	/**
	 * list() 只返回路径下的内容名称，以字符串形式
	 */
	@Test
	void list() {
		for (String item : FILE.list()) {
			log.info("路径下的内容 {}", item);
		}
	}

	/**
	 * 得到文件路径
	 *
	 * @param path 路径
	 */
	private List<File> getFileNames(String path) {
		File file = new File(path);
		assert file.exists() : "Path not found!";
		List<File> fileList = new ArrayList<>();
		File[] files = file.listFiles();
		for (File item : files) {
			if (item.isDirectory()) {
				fileList.addAll(getFileNames(item.getPath()));
			} else {
				fileList.add(item);
			}
		}
		return fileList;
	}
}
