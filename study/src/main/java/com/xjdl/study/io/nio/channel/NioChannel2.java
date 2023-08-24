package com.xjdl.study.io.nio.channel;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 复制文件
 */
@Slf4j
public class NioChannel2 {
	@Test
	void copy() {
		try (
				FileInputStream fis = new FileInputStream("");
				FileOutputStream fos = new FileOutputStream("");
				FileChannel fisChannel = fis.getChannel();
				FileChannel fosChannel = fos.getChannel()
		) {
			log.info("开始文件复制");
			// 2 * 80 MB
			ByteBuffer buffer = ByteBuffer.allocateDirect(2 * 81920000);
			int count = 0;
			while (true) {
				// 每循环一次清空缓冲区
				buffer.clear();
				int read = fisChannel.read(buffer);
				log.info("read  = {}", read);
				if (read == -1) {
					break;
				}
				buffer.flip();
				int write = fosChannel.write(buffer);
				log.info("write = {}", write);
				count++;
			}
			log.info("文件复制完成 {}", count);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
