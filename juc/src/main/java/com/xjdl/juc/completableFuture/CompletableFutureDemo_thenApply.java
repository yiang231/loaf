package com.xjdl.juc.completableFuture;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// 编排任务
public class CompletableFutureDemo_thenApply {
	private static void thenApplyWithDefaultExecutor(CompletableFuture<Integer> supplyAsync) {
		CompletableFuture<Integer> thenApplyAsync = supplyAsync.thenApplyAsync((integer -> {
			System.out.println(Thread.currentThread().getName() + "\t第一部分的返回值为\t" + integer);
			return integer += 6;
		}));
		thenApplyAsync.thenAcceptAsync((integer -> {
			System.out.println(Thread.currentThread().getName() + "\t第二部分的返回值为\t" + integer);
		}));
	}

	private static void thenApplyWithMyExecutor(ThreadPoolExecutor executor, CompletableFuture<Integer> supplyAsync) {
		CompletableFuture<Integer> thenApplyAsync = supplyAsync.thenApplyAsync((integer -> {
			System.out.println(Thread.currentThread().getName() + "\t第一部分的返回值为\t" + integer);
			return integer += 4;
		}), executor);
		thenApplyAsync.thenAcceptAsync((integer -> {
			System.out.println(Thread.currentThread().getName() + "\t第二部分的返回值为\t" + integer);
		}), executor);
	}

	private static void thenApply(CompletableFuture<Integer> supplyAsync) {
		CompletableFuture<Integer> thenApply = supplyAsync.thenApply((integer -> {
			System.out.println(Thread.currentThread().getName() + "\t第一部分的返回值为\t" + integer);
			return integer += 9;
		}));
		thenApply.thenAccept((integer -> {
			System.out.println(Thread.currentThread().getName() + "\t第二部分的返回值为\t" + integer);
		}));
	}

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
			int i = 1;
			return i; // 有返回值
		}, executor);
//		thenApply(supplyAsync);
		thenApplyWithMyExecutor(executor, supplyAsync);
//		thenApplyWithDefaultExecutor(supplyAsync);
		executor.shutdown();
		TimeUnit.SECONDS.sleep(3);
	}
}
