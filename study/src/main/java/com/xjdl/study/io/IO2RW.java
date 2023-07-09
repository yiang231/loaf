package com.xjdl.study.io;

import com.xjdl.study.springboot.banner.MyBanner;
import com.xjdl.study.util.MyUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * 简单IO流使用
 * TODO API 改造
 * @see MyBanner#printBanner(PrintStream)
 */
@Slf4j
public class IO2RW {
	/**
	 * 路径分隔符
	 */
	@Test
	void separator() {
		log.info("{}", File.separator); // \
		log.info("{}", File.separatorChar); // \
		log.info("{}", File.pathSeparator); // ;
		log.info("{}", File.pathSeparatorChar); // ;
	}

	/**
	 * 字节写文件_1
	 */
	@Test
	void byteWrite() throws Exception {
		log.info("开始字节写文件_1");
		FileOutputStream fileOutputStream = new FileOutputStream(MyUtils.getResourcePath("iotest/fileName.txt"), false); //存在数据转换
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
		bufferedOutputStream.write("字节写文件测试".getBytes("UTF-8"));
		bufferedOutputStream.close();
		fileOutputStream.close();
		log.info("结束字节写文件_1");
	}

	/**
	 * 字符写文件_2
	 */
	@Test
	void charWrite() throws Exception {
		log.info("开始字符写文件_2");
		FileWriter fileWriter = new FileWriter(MyUtils.getResourcePath("iotest/fileName.txt"), false); // 是否开启追加写入
//		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(""), "UTF-8"); // 指定字符集
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.append("写一行");// 为null写null
		bufferedWriter.newLine();
		bufferedWriter.append("换行");
		bufferedWriter.newLine();
		bufferedWriter.append("再写一行");
		bufferedWriter.newLine();
		bufferedWriter.append("换行");
		bufferedWriter.newLine();
		bufferedWriter.write("新写入的一行");
		bufferedWriter.newLine();
		bufferedWriter.write(""); // 有啥写啥
		bufferedWriter.newLine();
		bufferedWriter.append(null);
		bufferedWriter.newLine();
//		bufferedWriter.flush(); // flush是清空缓冲区，就是说立即输出到输出目的地，而不是等缓冲区满了再输出，write只是将数据输出到缓冲区，还没有输出到目的地
		bufferedWriter.close(); // close()之前会进行一次flush()
		log.info("结束字符写文件_2");
	}

	/**
	 * 字节读文件
	 */
	@Test
	void byteRead() throws Exception {
		log.info("开始字节读文件");
		FileInputStream fileInputStream = new FileInputStream(MyUtils.getResourcePath("iotest/fileName.txt"));
		int available = fileInputStream.available(); // 用于返回可以读取的剩余字节数，并且不会被下一次调用阻塞
		long skip = fileInputStream.skip(3); // 跳过给定数目的数据字节

		int length;
		byte[] bytes = new byte[8192];
		while ((length = fileInputStream.read(bytes)) != -1) {
			log.info(new String(bytes, 0, length));
		}
		fileInputStream.close();
		log.info("结束字节读文件");
	}

	/**
	 * 字符读文件_1
	 */
	@Test
	void charRead1() throws Exception {
		log.info("开始字符读文件_1");
		FileInputStream fileInputStream = new FileInputStream(MyUtils.getResourcePath("iotest/fileName.txt"));
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
		int length;
		char[] chars = new char[8192];
		while ((length = inputStreamReader.read(chars)) != -1) {
			log.info(new String(chars, 0, length));
		}
		inputStreamReader.close();
		fileInputStream.close();
		log.info("结束字节读文件_1");
	}

	/**
	 * 推荐使用
	 * 字符读文件_2
	 */
	@Test
	void charRead2() throws Exception {
		log.info("开始字符读文件_2");
		FileInputStream fileInputStream = new FileInputStream(MyUtils.getResourcePath("iotest/fileName.txt")); // 最好放File
		BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8); // 可以指定字符集
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader, 10 * 1024 * 1024); // 添加缓存参数10M

		int length;
		char[] chars = new char[8192];
		while ((length = bufferedReader.read(chars)) != -1) {
			log.info(new String(chars, 0, length));
		}
		bufferedReader.close();
		inputStreamReader.close();
		bufferedInputStream.close();
		fileInputStream.close();
		log.info("结束字节读文件_2");
	}

	/**
	 * 推荐使用
	 * 字符读文件_3
	 */
	@Test
	void charRead3() throws Exception {
		log.info("开始字符读文件_3");
		FileReader fileReader = new FileReader(MyUtils.getResourcePath("iotest/fileName.txt"));
		fileReader.getEncoding(); // 获取文件原来的编码方式
		BufferedReader bufferedReader = new BufferedReader(fileReader);
//        Stream<String> lines = bufferedReader.lines(); // 可以直接返回Stream流

//        int length;
//        char[] chars = new char[8192]; // 一次读取8192个字符【换行符也算字符】
////        byte[] bytes = new byte[8192]; // 字节流读取
//        while ((length = bufferedReader.read(chars)) != -1) {
//            log.info(new String(chars, 0, length));
//        }

		String line; // 一次读取一行，不包括换行符
		while ((line = bufferedReader.readLine()) != null) {
			log.info(line);
		}

//        int length;
//		while ((length = bufferedReader.read()) != -1) { // 读取单个字符
//			log.info("{}", (char) length);
//		}
		bufferedReader.close();
		log.info("结束字符读文件_3");
	}

	/**
	 * 边读边写
	 */
	@Test
	void readAndWrite() throws Exception {
		log.info("开始边读边写");
		// 字节读取流
		File fileRead = new File(MyUtils.getResourcePath("iotest/withRead.log"));
		FileInputStream fileInputStream = new FileInputStream(fileRead);

		// 字节写入流
		File file = new File(MyUtils.getResourcePath("iotest/withWrite.txt"));
		FileOutputStream fileOutputStream = new FileOutputStream(file);

		int length;
		byte[] bytes = new byte[8192];
		while ((length = fileInputStream.read(bytes)) != -1) {
			fileOutputStream.write(bytes, 0, length);
		}

		fileOutputStream.close();
		fileInputStream.close();
		log.info("结束边读边写");
	}

	/**
	 * 字符打印流
	 * PrintWriter
	 * print();
	 */

	/**
	 * Files 类是 JDK 7 添加的新的操作文件的类，它提供了提供了大量处理文件的方法，例如文件复制、读取、写入，获取文件属性、快捷遍历文件目录等，这些方法极大的方便了文件的操作
	 * write();
	 */
	/**
	 * 二进制文件读取 DataInputStream
	 */
	@Test
	void dataInputStream() {
		try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(MyUtils.getResourcePath("iotest/fileName.txt")))) {
			int length;
			byte[] data = new byte[8192];
			while ((length = dataInputStream.read(data)) > 0) {
				log.info("{}", new String(data, 0, length));
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
