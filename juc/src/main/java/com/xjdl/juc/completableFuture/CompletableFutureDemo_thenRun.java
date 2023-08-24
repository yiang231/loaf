package com.xjdl.juc.completableFuture;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// 编排任务
public class CompletableFutureDemo_thenRun {
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

		CompletableFuture<Integer> supplyAsync = CompletableFuture.supplyAsync(() -> {
			System.out.println(Thread.currentThread().getName() + "异步任务执行");
			int i = 1 + 1;
			return i; // 有返回值
		}, executor);
		// 无法接收supplyAsync的返回值
		supplyAsync.thenRun(() -> {
			System.out.println(Thread.currentThread().getName() + "\t supplyAsync thenRun");
		});
		supplyAsync.thenRunAsync(() -> {
			System.out.println(Thread.currentThread().getName() + "\t supplyAsync thenRunAsync");
		});
		supplyAsync.thenRunAsync(() -> {
			System.out.println(Thread.currentThread().getName() + "\t supplyAsync thenRunAsync executor");
		}, executor);
		executor.shutdown();
		TimeUnit.SECONDS.sleep(3);
	}
}
