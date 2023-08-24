package com.xjdl.juc.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 自定义类 实现Runnable接口 重写run方法
 */
@Slf4j
public class ThreadDemo1 {
	@Test
	void test() {
		new Thread(() -> {
			log.info("{}", "匿名内部类实现定义线程 by Runnable");
		}).start();
	}
}
