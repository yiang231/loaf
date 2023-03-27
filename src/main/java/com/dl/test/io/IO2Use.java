package com.dl.test.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class IO2Use {
	public static void main(String[] args) throws Exception {
//		charRead2();
		readAndWrite();

		System.out.println(File.separator); // \
		System.out.println(File.separatorChar); // \
		System.out.println(File.pathSeparator); // ;
		System.out.println(File.pathSeparatorChar); // ;
	}

	/**
	 * 字节写文件_1
	 */
	public static void byteWrite() throws Exception {
		System.out.println("开始字节写文件_1");
		FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/iotest/fileName.txt", false); //存在数据转换
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
		bufferedOutputStream.write("字节写文件测试".getBytes("UTF-8"));
		bufferedOutputStream.close();
		fileOutputStream.close();
		System.out.println("结束字节写文件_1");
	}

	/**
	 * 字符写文件_2
	 */
	public static void charWrite() throws Exception {
		System.out.println("开始字符写文件_2");
		FileWriter fileWriter = new FileWriter("src/main/resources/iotest/fileName.txt", false); // 是否开启追加写入
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
		System.out.println("结束字符写文件_2");
	}

	/**
	 * 字节读文件
	 */
	public static void byteRead() throws Exception {
		System.out.println("开始字节读文件");
		FileInputStream fileInputStream = new FileInputStream("src/main/resources/iotest/fileName.txt");
		int available = fileInputStream.available(); // 用于返回可以读取的剩余字节数，并且不会被下一次调用阻塞
		long skip = fileInputStream.skip(3); // 跳过给定数目的数据字节

		int length;
		byte[] bytes = new byte[8192];
		while ((length = fileInputStream.read(bytes)) != -1) {
			System.out.println(new String(bytes, 0, length));
		}
		fileInputStream.close();
		System.out.println("结束字节读文件");
	}

	/**
	 * 字符读文件_1
	 */
	public static void charRead1() throws Exception {
		System.out.println("开始字符读文件_1");
		FileInputStream fileInputStream = new FileInputStream("src/main/resources/iotest/fileName.txt");
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
		int length;
		char[] chars = new char[8192];
		while ((length = inputStreamReader.read(chars)) != -1) {
			System.out.println(new String(chars, 0, length));
		}
		inputStreamReader.close();
		fileInputStream.close();
		System.out.println("结束字节读文件_1");
	}

	/**
	 * 推荐使用
	 * 字符读文件_2
	 */
	public static void charRead2() throws Exception {
		System.out.println("开始字符读文件_2");
		FileInputStream fileInputStream = new FileInputStream("src/main/resources/iotest/fileName.txt"); // 最好放File
		BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8); // 可以指定字符集
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader, 10 * 1024 * 1024); // 添加缓存参数10M

		int length;
		char[] chars = new char[8192];
		while ((length = bufferedReader.read(chars)) != -1) {
			System.out.println(new String(chars, 0, length));
		}
		bufferedReader.close();
		inputStreamReader.close();
		bufferedInputStream.close();
		fileInputStream.close();
		System.out.println("结束字节读文件_2");
	}

	/**
	 * 推荐使用
	 * 字符读文件_3
	 */
	public static void charRead3() throws Exception {
		System.out.println("开始字符读文件_3");
		FileReader fileReader = new FileReader("src/main/resources/iotest/fileName.txt");
		fileReader.getEncoding(); // 获取文件原来的编码方式
		BufferedReader bufferedReader = new BufferedReader(fileReader);
//        Stream<String> lines = bufferedReader.lines(); // 可以直接返回Stream流

//        int length;
//        char[] chars = new char[8192]; // 一次读取8192个字符【换行符也算字符】
////        byte[] bytes = new byte[8192]; // 字节流读取
//        while ((length = bufferedReader.read(chars)) != -1) {
//            System.out.println(new String(chars, 0, length));
//        }

		String line; // 一次读取一行，不包括换行符
		while ((line = bufferedReader.readLine()) != null) {
			System.out.println(line);
		}

//        int length;
//        while ((length = bufferedReader.read()) != -1) { // 读取单个字符
//            System.out.println((char) length);
//        }
		bufferedReader.close();
		System.out.println("结束字符读文件_3");
	}

	/**
	 * 边读边写
	 */
	public static void readAndWrite() throws Exception {
		System.out.println("开始边读边写");
		// 字节读取流
		String pathRead = "src/main/resources/iotest/withRead.log";
		File fileRead = new File(pathRead);
		FileInputStream fileInputStream = new FileInputStream(fileRead);

		// 字节写入流
		File file = new File("src/main/resources/iotest/withWrite.txt");
		String pathWrite = "";
		FileOutputStream fileOutputStream = new FileOutputStream(file);

		int length;
		byte[] bytes = new byte[8192];
		while ((length = fileInputStream.read(bytes)) != -1) {
			fileOutputStream.write(bytes, 0, length);
		}

		fileOutputStream.close();
		fileInputStream.close();
		System.out.println("结束边读边写");
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
}
