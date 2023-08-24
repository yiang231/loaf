package com.xjdl.juc.lock;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * try{
 * <p>
 * <p>
 * }finally{
 * <p>
 * <p>
 * }
 */
public class Cache {
	HashMap cache = new HashMap();
	ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true); // 读写锁改造

	/**
	 * 写入缓存
	 *
	 * @param key
	 * @param value
	 * @throws InterruptedException
	 */
	public void set(String key, String value) throws InterruptedException {
		rwl.writeLock().lock();
		try {
			System.out.println(Thread.currentThread().getName());
			System.out.println("开始写入...");
			TimeUnit.SECONDS.sleep(1);
			cache.put(key, value);
			System.out.println("写入成功...");
		} finally {
			rwl.writeLock().unlock();
		}
	}

	/**
	 * 读取缓存
	 *
	 * @param key
	 * @throws InterruptedException
	 */
	public void get(String key) throws InterruptedException {
		rwl.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName());
			System.out.println("开始读取...");
			TimeUnit.SECONDS.sleep(1);
			Object o = cache.get(key);
			System.out.println("读取成功...");
		} finally {
			rwl.readLock().unlock();
		}
	}
}
