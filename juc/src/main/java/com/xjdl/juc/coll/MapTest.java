package com.xjdl.juc.coll;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class MapTest {
	private static void concurrentHashMap(ConcurrentHashMap<Object, Object> concurrentHashMap) throws InterruptedException {
		Thread t1 = new Thread(() -> {
			concurrentHashMap.put("通话", new Object()); //1179410
		}, "t1");
		Thread t2 = new Thread(() -> {
			concurrentHashMap.put("重地", new Object()); //1179410
		}, "t2");
		t1.start();
		t2.start();
		t1.join(); // 插队
		t2.join();
		System.out.println(concurrentHashMap);
	}

	private static void dataLost(HashMap<Object, Object> hashMap) throws InterruptedException {
		Thread t1 = new Thread(() -> {
			hashMap.put("通话", new Object()); //1179410
		}, "t1");
		Thread t2 = new Thread(() -> {
			hashMap.put("重地", new Object()); //1179410
		}, "t2");
		t1.start();
		t2.start();
		t1.join(); // 插队
		t2.join();
		System.out.println(hashMap);
	}

	private static void mapError(HashMap<Object, Object> hashMap) {
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				hashMap.put(new Object(), new Object());
			}).start();
			System.out.println(hashMap);
		}
	}

	@Test
	void test() throws InterruptedException {
		// HashMap问题，并发情况下数据丢失
		HashMap<Object, Object> hashMap = new HashMap<>(); // key的hashcode值，无符号右移16位作异或运算 (h = key.hashCode()) ^ (h >>> 16)
		ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<>();
//		mapError(hashMap);
//		dataLost(hashMap); // 并发数据丢失
		concurrentHashMap(concurrentHashMap); // 解决方法
		// java.util.ConcurrentModificationException
//		Thread.sleep(1000);
	}
}
