package com.xjdl.juc.thread;

import org.junit.jupiter.api.Test;

public class ThreadLocalDemo {
	@Test
	void test() {
		ThreadLocal<Object> threadLocal = new ThreadLocal<>();
		threadLocal.set(1);
		System.out.println("threadLocal.get() = " + threadLocal.get());

		MyThread myThread = new MyThread();
		myThread.start();
	}
}
