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
public class NioChannel4 {
	@Test
	void copy() {
		try (
				FileInputStream fis = new FileInputStream("");
				FileOutputStream fos = new FileOutputStream("");
				FileChannel fisChannel = fis.getChannel();
				FileChannel fosChannel = fos.getChannel()
		) {
			log.info("开始文件复制");
			fosChannel.transferFrom(fisChannel, fisChannel.position(), fisChannel.size());
//			fisChannel.transferTo(fosChannel.size(), fosChannel.position(), fosChannel);
			log.info("文件复制完成");
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
