package com.xjdl.juc.juc_3utils;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

// 信号量
public class SemaphoreDemo {
	@Test
	void test() throws InterruptedException {
		/*主要用于两个目的：
		1. 多个共享资源的互斥使用。
		2. 用于并发线程数的控制。保护一个关键部分不要一次输入超过N个线程*/
		// 6辆车抢占3个车位
		Semaphore semaphore = new Semaphore(3);
		for (int i = 0; i < 6; i++) {
			new Thread(() -> {
				try {
					semaphore.acquire(); // 通知其他前程，当前线程已抢到
					System.out.println("第" + Thread.currentThread().getName() + "线程已抢到");
					TimeUnit.SECONDS.sleep(new Random().nextInt(5)); // 模拟处理业务
					semaphore.release(); // 通知其他线程，当前线程已释放
					System.out.println("第" + Thread.currentThread().getName() + "线程已释放");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}, String.valueOf(i)).start();
		}
		//第0线程已抢到
		//第1线程已抢到
		//第2线程已抢到
		//第2线程已释放
		//第3线程已抢到
		//第1线程已释放
		//第5线程已抢到
		//第0线程已释放
		//第4线程已抢到
		//第3线程已释放
		//第5线程已释放
		//第4线程已释放
		Thread.sleep(100000);
	}
}
