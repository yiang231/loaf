package com.xjdl.juc.unsafe;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

// 原子操作的包装类
public class CasDemo {
	@Test
	void test() {
		AtomicInteger i = new AtomicInteger(1);
		System.out.println("第一次更新：" + i.compareAndSet(1, 200));
		System.out.println("第一次更新后i的值：" + i.get());
		System.out.println("第二次更新：" + i.compareAndSet(1, 300));
		System.out.println("第二次更新后i的值：" + i.get());
		System.out.println("第三次更新：" + i.compareAndSet(200, 300));
		System.out.println("第三次更新后i的值：" + i.get());
	}
}
