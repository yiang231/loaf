package com.xjdl.juc.Callable;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MyCallable {
	@Test
	void test() {
		FutureTask<String> futureTask = new FutureTask<>(new CallableDemo()); // 异步调用
		Thread thread1 = new Thread(futureTask, "callable线程1");
		Thread thread2 = new Thread(futureTask, "callable线程2");
		thread1.start();
		thread2.start();
		try {
			// 同一个futureTask只会获取一次结果，创建两个futureTask打印两次
			System.out.println(futureTask.get()); // 得到返回结果，会阻塞main线程，一般放到最后
			System.out.println(futureTask.isDone()); // 是否执行完成
			System.out.println(Thread.currentThread().getName());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}
