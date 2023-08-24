package com.xjdl.juc.juc_3utils;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

// 死锁CountDownLatch
public class CountDownLatchDemo {
	@Test
	void test() {
		// 初始化计数器为6
		CountDownLatch countDownLatch = new CountDownLatch(6);
		for (int i = 0; i < 6; i++) {
			new Thread(() -> {
				try {
					TimeUnit.SECONDS.sleep(new Random().nextInt(5));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "\t 离开了");
				// 计数器减一
				countDownLatch.countDown();
			}, String.valueOf(i)).start();
		}
		try {
			// 没减到零之前，主线程一直处于阻塞状态
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "\t 门已经焊死，谁都别想走");
	}
}
