package com.xjdl.juc.volatileKeyWord;

import java.util.concurrent.atomic.AtomicInteger;

public class Resource {
	//	volatile int num = 0; // 不保证原子性
	AtomicInteger atomicInteger = new AtomicInteger(0);

	public synchronized Integer addPlus() {
//	public Integer addPlus() {
//		return ++num;
		return atomicInteger.incrementAndGet(); //使用CAS[比较并交换]保证原子性
	}
}
