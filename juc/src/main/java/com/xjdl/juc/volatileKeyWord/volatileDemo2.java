package com.xjdl.juc.volatileKeyWord;

import org.junit.jupiter.api.Test;

// 验证不保证原子性
public class volatileDemo2 {
	@Test
	void test() {
		/*当把变量声明为volatile类型后，编译器在运行时都会注意到这个变量是共享的，因此**不会将该变量上的操作与其他内存操作一起重排序。
		 在访问volatile变量时不会执行加锁操作，因此也就不会使执行线程阻塞，因此volatile变量是一种比sychronized关键字更轻量级的同步机制。*/
		Resource resource = new Resource();
		for (int i = 0; i < 1000; i++) {
			new Thread(() -> {
				System.out.println(resource.addPlus());
			}).start();
		}
	}
}
