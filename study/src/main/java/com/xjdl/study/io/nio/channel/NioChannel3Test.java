package com.xjdl.study.io.nio.channel;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 数据分散与聚集
 */
@Slf4j
public class NioChannel3Test {
	@Test
	void copy() {
		try (
				RandomAccessFile rafi = new RandomAccessFile("", "r");
				RandomAccessFile rafo = new RandomAccessFile("", "rw");
				FileChannel fisChannel = rafi.getChannel();
				FileChannel fosChannel = rafo.getChannel();
		) {
			log.info("开始文件复制");
			// 2 * 80 MB
			ByteBuffer buffer1 = ByteBuffer.allocateDirect(81920000);
			ByteBuffer buffer2 = ByteBuffer.allocateDirect(81920000);
			ByteBuffer[] buffers = {buffer1, buffer2};
			int count = 0;
			while (true) {
				// 每循环一次清空缓冲区
				for (ByteBuffer buffer : buffers) {
					buffer.clear();
				}
				long read = fisChannel.read(buffers);
				log.info("read  = {}", read);
				if (read == -1) {
					break;
				}
				for (ByteBuffer buffer : buffers) {
					buffer.flip();
				}
				long write = fosChannel.write(buffers);
				log.info("write = {}", write);
				count++;
			}
			log.info("文件复制完成 {}", count);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
