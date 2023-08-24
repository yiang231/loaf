package com.xjdl.juc.coll;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListTest {
	private static void useSynchronizedList(List<String> synchronizedList) {
		for (int i = 0; i < 200; i++) {
			new Thread(() -> {
				synchronizedList.add(UUID.randomUUID().toString());
				synchronizedList.forEach(System.out::println);
			}).start();
		}
	}

	private static void useVector(Vector<String> vector) {
		vector.add("1");
		vector.add("2");
		vector.add("3");
		vector.add("4");

		for (int i = 0; i < 1000; i++) {
			new Thread(() -> {
				vector.add(UUID.randomUUID().toString());
				vector.forEach(System.out::println);
			}).start();
		}
	}

	private static void copyOnWriteArrayList(CopyOnWriteArrayList<String> copyOnWriteArrayList) {
		copyOnWriteArrayList.add("1");
		copyOnWriteArrayList.add("2");
		copyOnWriteArrayList.add("3");
		copyOnWriteArrayList.add("4");
		Iterator<String> iterator = copyOnWriteArrayList.iterator();// 使用独有的迭代器
		while (iterator.hasNext()) {
			String next = iterator.next();
			if (next.equals("3"))
				copyOnWriteArrayList.add("yyy");
		}
		copyOnWriteArrayList.forEach(System.out::println);
	}

	private static void listIterator(ArrayList<String> arrayList) {
		arrayList.add("1");
		arrayList.add("2");
		arrayList.add("3");
		ListIterator<String> listIterator = arrayList.listIterator();// 使用独有的迭代器
		while (listIterator.hasNext()) {
			String next = listIterator.next();
			if (next.equals("3"))
				listIterator.add("yyy");
		}
		arrayList.forEach(System.out::println);
	}

	private static void listError1(ArrayList<String> arrayList) {
		for (int i = 0; i < 20; i++) {
			new Thread(() -> {
				arrayList.add(UUID.randomUUID().toString());
				arrayList.forEach(System.out::println);
			}).start();
		}
	}

	@Test
	void test() throws InterruptedException {
		ArrayList<String> arrayList = new ArrayList<>();
		CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>(); // 本质为数组,使用的是可重入锁
		Vector<String> vector = new Vector<>();
		List<String> synchronizedList = Collections.synchronizedList(new ArrayList<>());
		listError1(arrayList);
//		listIterator(arrayList); // 解决办法一
//		copyOnWriteArrayList(copyOnWriteArrayList); // 解决办法二 优先
//		useVector(vector); // 解决办法三 // public synchronized boolean add(E e)
//		useSynchronizedList(synchronizedList); // 解决办法四
		// java.util.ConcurrentModificationException	并发修改异常 保护集合安全性
	}
}
