package com.dl.test.LocalCache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 我们结合平常使用的Redis来想下，自己实现本地缓存需要考虑哪些因素呢，我这里总结了三点：
 * <p>
 * 数据存储，基于Java实现的话我首先想到的是key-value结构的集合，如HashMap，并发环境下的话使用ConcurrentHashMap、随着访问顺序元素位置会变化的LinkedHashMap
 * 缓存过期删除策略，参考Redis的定期删除和惰性删除
 * 缓存淘汰策略，有先进先出、最少使用、最近最少使用（LRU）、随机等策略。
 * 由以上可知，
 * <p>
 * 要实现缓存过期删除的话，需要记录元素的生效时间，可以实现一个监控线程或用ScheduledExecutorService实现延迟删除。
 * 缓存淘汰策略采用最近最少使用（LRU）的话，需要维护一个元素的使用队列
 * 实现方式需要涉及的Java类有两种方式：
 * <p>
 * ReentrantLock+ConcurrentHashMap + ConcurrentLinkedQueue
 * ReentrantLock+ LinkedHashMap（本文以这种方式实现）
 */
public class LocalCache {
	//默认的缓存容量
	private static final int DEFAULT_CAPACITY = 16;
	private final ReentrantLock lock = new ReentrantLock();
	// 0用完就关还是继续等待
	private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(0);

	public LocalCache() {
	}

	static LocalCache getInstance() {
		return CacheLocal.cache;
	}

	/**
	 * 添加元素时设置带有过期时间的value
	 *
	 * @param key
	 * @param value
	 * @param expire
	 * @param timeUnit
	 */
	public void put(String key, String value, long expire, TimeUnit timeUnit) {
		if (timeUnit == null) {
			timeUnit = TimeUnit.MILLISECONDS;
		}
		lock.lock();
		try {
			CacheData data = new CacheData();
			data.setKey(key);
			data.setValue(value);
			linkedHashMap.put(key, data);
			if (expire > 0) {
				removeAfterExpireTime(key, expire, timeUnit);
			}
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 获取指定的key
	 */
	public CacheData get(String key) {
		lock.lock();
		try {
			// 传入一个key，有的话返回对应的value，没有则返回给定的value的默认值
			return linkedHashMap.getOrDefault(key, new CacheData());
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 删除过期的缓存
	 *
	 * @param key
	 * @param expireTime
	 * @param unit
	 */
	private void removeAfterExpireTime(String key, long expireTime, TimeUnit unit) {
		scheduledExecutorService.schedule(() -> {
			linkedHashMap.remove(key);
		}, expireTime, unit);
	}

	private static class CacheLocal {
		private static final LocalCache cache = new LocalCache();
	}

	static class CacheData {

		private String key;

		private String value;

		public String getValue() {
			return value;
		}

		private void setValue(String value) {
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		private void setKey(String key) {
			this.key = key;
		}

		@Override
		public String toString() {
			return "CacheData{" +
					"key='" + key + '\'' +
					", value='" + value + '\'' +
					'}';
		}
	}

	/**
	 * LinkedHashMap
	 * 有序的双向链表
	 * 非线程安全
	 */
	private static final Map<String, CacheData> linkedHashMap = new LinkedHashMap<String, CacheData>(DEFAULT_CAPACITY, 0.75f, true) {
		@Override
		protected boolean removeEldestEntry(Map.Entry eldest) {
			//缓存淘汰
			//最大容量
			int MAX_CAPACITY = 64;
			return MAX_CAPACITY < linkedHashMap.size();
		}
	};
}
