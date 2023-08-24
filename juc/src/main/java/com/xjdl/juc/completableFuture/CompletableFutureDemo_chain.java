package com.xjdl.juc.completableFuture;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// 链式调用
public class CompletableFutureDemo_chain {
	@Test
	void test() throws InterruptedException {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(121, 423, 2L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(4343), new ThreadFactory() {
			int i = 1;

			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				thread.setName("核心线程池\t" + i++);
				return thread;
			}
		}, new ThreadPoolExecutor.CallerRunsPolicy());
		CompletableFuture.supplyAsync(() -> {
					System.out.println(Thread.currentThread().getName() + "异步任务执行");
					int i = 1;
					return i; // 有返回值
				}, executor)
				.thenApplyAsync((integer -> {
					System.out.println(Thread.currentThread().getName() + "\t第一部分的返回值为\t" + integer);
					return integer += 12;
				}), executor)
				.thenApplyAsync(integer -> {
					throw new NullPointerException();
//					return Thread.currentThread().getName() + "\t第二部分的返回值为\t" + integer;
				}, executor)
				.whenCompleteAsync((unused, throwable) -> {
					if (throwable != null) {
						throwable.printStackTrace();
					}
					System.out.println(unused);
				});
		executor.shutdown();
		TimeUnit.SECONDS.sleep(3);
	}
}
