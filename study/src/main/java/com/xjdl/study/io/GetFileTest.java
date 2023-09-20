package com.xjdl.study.io;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * copy 拷贝文件
 * createDirectory 创建一个目录
 * createDirectories 创建多级目录
 * move 移动文件
 * delete 删除文件，删除目录【不允许有内容】
 *
 * @see Files
 * @see Paths
 */
@Slf4j
public class GetFileTest {

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

	/**
	 * rm -rf
	 * 遍历目录【访问者模式】
	 */
	@Test
	void walkFileTree() {
		AtomicInteger dirCount = new AtomicInteger();
		AtomicInteger fileCount = new AtomicInteger();
		try {
			// 包含初始文件夹本身
			Files.walkFileTree(Paths.get(PROPERTY), new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					log.info("{} ===>", dir);
					dirCount.incrementAndGet();
					return super.preVisitDirectory(dir, attrs);
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					log.info("===> {}", file);
					fileCount.incrementAndGet();
					return super.visitFile(file, attrs);
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					log.error("visit [{" + file + "}] Failed", exc);
					return super.visitFileFailed(file, exc);
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					log.debug("<=== {}", dir);
					return super.postVisitDirectory(dir, exc);
				}
			});
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		log.info("dirCount = {}, fileCount = {}", dirCount, fileCount);
	}

	/**
	 * 拷贝文件夹
	 */
	@Test
	void test() throws IOException {
		try (Stream<Path> pathStream = Files.walk(Paths.get(PROPERTY))) {
			pathStream.forEach(path -> {
				try {
					String target = path.toString().replace(PROPERTY, "");
					Path targetPath = Paths.get(target);
					if (Files.isDirectory(path)) {
						Files.createDirectory(targetPath);
					} else if (Files.isRegularFile(path)) {
						Files.copy(path, targetPath);
					}
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			});
		}
	}
}
