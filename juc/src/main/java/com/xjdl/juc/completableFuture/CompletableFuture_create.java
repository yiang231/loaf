package com.xjdl.juc.completableFuture;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// 创建异步任务
public class CompletableFuture_create {
	@Test
	void test() throws InterruptedException, ExecutionException {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(
				5
				, 10
				, 1L
				, TimeUnit.MINUTES
				, new ArrayBlockingQueue<>(1000)
				, new ThreadFactory() {
			int i = 1;

			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				thread.setName("核心线程[" + i++ + "]");
				return thread;
			}
		}
				, new ThreadPoolExecutor.CallerRunsPolicy()
		);
		// 使用默认线程池的runAsync异步任务 无返回值
		CompletableFuture<Void> runAsync1 = CompletableFuture.runAsync(() -> {
			System.out.println(Thread.currentThread().getName() + "\t 使用默认线程池的runAsync异步任务 无返回值");
		});
		// 使用自定义线程池的runAsync异步任务 无返回值
		CompletableFuture<Void> runAsync2 = CompletableFuture.runAsync(() -> {
			System.out.println(Thread.currentThread().getName() + "\t 使用自定义线程池的runAsync异步任务 无返回值");
		}, executor);
		// 使用默认线程池的supplyAsync异步任务 有返回值
		CompletableFuture<String> supplyAsync1 = CompletableFuture.supplyAsync(() -> {
			return Thread.currentThread().getName() + "使用默认线程池的supplyAsync异步任务 有返回值";
		});
		System.out.println(supplyAsync1.get()); // 获取返回值的方法,会阻塞主线程
		// 使用自定义线程池的supplyAsync异步任务 有返回值
		CompletableFuture<String> supplyAsync2 = CompletableFuture.supplyAsync(() -> {
			return Thread.currentThread().getName() + "使用自定义线程池的supplyAsync异步任务 有返回值";
		}, executor);
		System.out.println(supplyAsync2.get());
		executor.shutdown();
		TimeUnit.SECONDS.sleep(2);
		System.out.println(Thread.currentThread().getName());
	}
}
