package com.xjdl.study.io.nio.channel;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 通道数据转移
 */
@Slf4j
public class NioChannel4Test {
	@Test
	void copy() {
		try (
				FileInputStream fis = new FileInputStream("");
				FileOutputStream fos = new FileOutputStream("");
				FileChannel fisChannel = fis.getChannel();
				FileChannel fosChannel = fos.getChannel()
		) {
			log.info("开始文件复制");
			// 效率高，底层会利用操作系统的零拷贝进行优化，单次数据大小 2GB
			fosChannel.transferFrom(fisChannel, fisChannel.position(), fisChannel.size());
//			fisChannel.transferTo(fosChannel.size(), fosChannel.position(), fosChannel);
			log.info("文件复制完成");
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 大文件传输
	 */
	@Test
	void bigTransfer() {
		try (
				FileInputStream fis = new FileInputStream("");
				FileOutputStream fos = new FileOutputStream("");
				FileChannel fisChannel = fis.getChannel();
				FileChannel fosChannel = fos.getChannel()
		) {
			log.info("开始文件复制");
			// 剩余字节数
			long size = fisChannel.size();
			for (long left = size; left > 0; ) {
				log.info("position: {} left: {}", size - left, left);
				left -= fisChannel.transferTo(size - left, left, fosChannel);
			}
			log.info("文件复制完成");
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
