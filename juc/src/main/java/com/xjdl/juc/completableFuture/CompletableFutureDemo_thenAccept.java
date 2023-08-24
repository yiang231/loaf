package com.xjdl.juc.completableFuture;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// 编排任务
public class CompletableFutureDemo_thenAccept {
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
		// 可以接受上一步的返回值，自己无返回值
		CompletableFuture<Void> thenAccept1 = supplyAsync.thenAccept((integer) -> {
			System.out.println(Thread.currentThread().getName() + "\t上一部分的返回值为\t" + integer);
		});
		CompletableFuture<Void> thenAcceptAsync = supplyAsync.thenAcceptAsync(integer -> {
			System.out.println(Thread.currentThread().getName() + "\t上一部分的返回值为\t" + integer);
		});
		CompletableFuture<Void> thenAcceptAsync1 = supplyAsync.thenAcceptAsync(integer -> {
			System.out.println(Thread.currentThread().getName() + "\t上一部分的返回值为\t" + integer);
		}, executor);
		executor.shutdown();
		TimeUnit.SECONDS.sleep(3);
	}
}
