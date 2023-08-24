package com.xjdl.study.io.nio.channel;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * FileChannel 读写
 */
@Slf4j
public class NioChannel1 {
	@Test
	void write() {
		try (
				FileOutputStream fos = new FileOutputStream("");
				FileChannel channel = fos.getChannel()
		) {
			ByteBuffer buffer = ByteBuffer.allocate(8192);
			buffer.put("Hello World!".getBytes(StandardCharsets.UTF_8));
			buffer.flip();
			int write = channel.write(buffer);
			log.info("写入文件完毕 {}", write);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	@Test
	void read() {
		try (
				FileInputStream fis = new FileInputStream("");
				FileChannel channel = fis.getChannel()
		) {
			ByteBuffer buffer = ByteBuffer.allocate(8192);
			channel.read(buffer);
			buffer.flip();
			log.info("读取文件完毕\n\t\r{}", new String(buffer.array(), 0, buffer.remaining()));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
