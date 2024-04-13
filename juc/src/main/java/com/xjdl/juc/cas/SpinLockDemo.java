package com.xjdl.juc.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author yanglin
 * @create 2024-04-13 21:48 星期六
 * description:  自旋锁
 */
@Slf4j
public class SpinLockDemo {
	private final AtomicReference<Thread> atomicReference = new AtomicReference<>();

	/**
	 * 加锁
	 */
	public void lock() {
		Thread thread = Thread.currentThread();
		//一直尝试抢锁
		while (!atomicReference.compareAndSet(null, thread)) {

		}
		log.info(Thread.currentThread().getName() + "\t come in!");
	}

	/**
	 * 解锁
	 */
	public void unlock() {
		Thread thread = Thread.currentThread();
		//解锁
		atomicReference.compareAndSet(thread, null);
		log.info(Thread.currentThread().getName() + "\t task over!");
	}
}
