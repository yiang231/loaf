package com.xjdl.study.io.nio.buffer;

import com.xjdl.study.io.nio.util.ByteBufUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class NioBuffer1Test {
	/**
	 * 简单使用
	 */
	@Test
	void test1() {
		// 创建输入流
		try (
				FileInputStream fis = new FileInputStream("");
				FileChannel channel = fis.getChannel()
		) {
			// 准备缓冲区，默认为写模式
			ByteBuffer buffer = ByteBuffer.allocate(12);
			// 通道读取数据写入缓冲区
			while (true) {
				int read = channel.read(buffer);
				log.info("读取到的字节数 {}", read);
				if (read == -1) {
					break;
				}
				// 切换至读模式
				buffer.flip();
				// 是否有数据剩余
				while (buffer.hasRemaining()) {
					byte b = buffer.get();
					log.info("{}", (char) b);
				}
				buffer.clear();
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 基本api1
	 */
	@Test
	void test2() {
		ByteBuffer buffer = ByteBuffer.allocate(16);
		buffer.put((byte) 0x61); // 'a'
		ByteBufUtil.debugAll(buffer);
		buffer.put(new byte[]{0x62, 0x63, 0x64}); // b  c  d
		ByteBufUtil.debugAll(buffer);
//		log.info("{}", buffer.get());
		buffer.flip();
		log.info("{}", buffer.get());
		ByteBufUtil.debugAll(buffer);
		// 将未读取的数据向前移
		buffer.compact();
		ByteBufUtil.debugAll(buffer);
		buffer.put(new byte[]{0x65, 0x6f});
		ByteBufUtil.debugAll(buffer);
	}

	/**
	 * 基本api2
	 */
	@Test
	void test3() {
		ByteBuffer buffer = ByteBuffer.allocate(10);
		buffer.put(new byte[]{'a', 'b', 'c', 'd'});
		buffer.flip();

		// rewind 从头开始读
		buffer.get(new byte[4]);
		ByteBufUtil.debugAll(buffer);
		buffer.rewind();
		log.info("{}", (char) buffer.get());

		// mark & reset
		// mark 做一个标记，记录 position 位置， reset 是将 position 重置到 mark 的位置
//        log.info("{}",(char) buffer.get());
//        log.info("{}",(char) buffer.get());
//        buffer.mark(); // 加标记，索引2 的位置
//        log.info("{}",(char) buffer.get());
//        log.info("{}",(char) buffer.get());
//        buffer.reset(); // 将 position 重置到索引 2
//        ByteBufUtil.debugAll(buffer);
//        log.info("{}",(char) buffer.get());
//        log.info("{}",(char) buffer.get());

		// get(i) 不会改变读索引的位置
//		log.info("{}", (char) buffer.get(3));
//		ByteBufUtil.debugAll(buffer);
	}

	/**
	 * 转换
	 */
	@Test
	void test4() {
		// 1. 字符串转为 ByteBuffer
		ByteBuffer buffer1 = ByteBuffer.allocate(16);
		buffer1.put("hello".getBytes());
		ByteBufUtil.debugAll(buffer1);

		// 2. Charset 自动切换读取模式
		ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("hello");
		ByteBufUtil.debugAll(buffer2);

		// 3. wrap 自动切换读取模式
		ByteBuffer buffer3 = ByteBuffer.wrap("hello".getBytes());
		ByteBufUtil.debugAll(buffer3);

		// 4. 转为字符串
		String str1 = StandardCharsets.UTF_8.decode(buffer2).toString();
		log.info("{}", str1);

		buffer1.flip();
		String str2 = StandardCharsets.UTF_8.decode(buffer1).toString();
		log.info("{}", str2);
	}

	/**
	 * 类型
	 */
	@Test
	void test5() {
		log.info("堆内存 {}", ByteBuffer.allocate(8).getClass().getName());
		log.info("直接内存 {}", ByteBuffer.allocateDirect(8).getClass().getName());
	}
}
