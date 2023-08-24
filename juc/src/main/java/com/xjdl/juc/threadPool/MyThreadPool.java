package com.xjdl.juc.threadPool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// 自定义线程池
public class MyThreadPool {
	@Test
	void test() throws InterruptedException {
		ExecutorService threadPoolExecutor = new ThreadPoolExecutor(
				10
				, 100
				, 1L
				, TimeUnit.SECONDS
				, new ArrayBlockingQueue<>(5)
//				, Executors.defaultThreadFactory()
				, new ThreadFactory() {
			int i = 1;

			@Override
			public Thread newThread(Runnable r) {

				Thread thread = new Thread(r);
				thread.setName("核心线程[" + i++ + "]");
				return thread;
			}
		}
//				, new ThreadPoolExecutor.CallerRunsPolicy()
				, new RejectedExecutionHandler() {
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				System.out.println("我拒绝");
			}
		}
		);

		for (int i = 0; i < 10; i++) {
			threadPoolExecutor.execute(() -> {
				System.out.println("第\t" + Thread.currentThread().getName() + "\t号线程");
			});
		}
		threadPoolExecutor.shutdown();
		for (int i = 0; i < 10; i++) {
			System.out.println("第\t" + Thread.currentThread().getName() + "\t号线程");
		}

		Thread.sleep(5000);
	}
}
