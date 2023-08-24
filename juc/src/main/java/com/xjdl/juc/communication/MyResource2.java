package com.xjdl.juc.communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyResource2 {
	public String str = "A";
	ReentrantLock reentrantLock = new ReentrantLock();
	Condition c1 = reentrantLock.newCondition();
	Condition c2 = reentrantLock.newCondition();
	Condition c3 = reentrantLock.newCondition();

	public synchronized void printA5() throws InterruptedException {
		reentrantLock.lock();
		// 判断
		try {
			while (!str.equals("A")) // 使用while避免虚假唤醒
				c1.await();
			// 干活
			for (int i = 0; i < 5; i++) {
				System.out.print("A");
			}
			str = "B";
			// 通知
			c1.signal();
		} finally {
			reentrantLock.unlock();
		}
	}

	public synchronized void printB10() throws InterruptedException {
		reentrantLock.lock();
		try {
			// 判断
			while (!str.equals("B")) // 使用while避免虚假唤醒
				c2.await();
			// 干活
			for (int i = 0; i < 10; i++) {
				System.out.print("B");
			}
			str = "C";
			// 通知
			c2.signal();
		} finally {
			reentrantLock.unlock();
		}
	}

	public synchronized void printC15() throws InterruptedException {
		reentrantLock.lock();
		try {
			// 判断
			while (!str.equals("C")) // 使用while避免虚假唤醒
				c3.await();
			// 干活
			for (int i = 0; i < 15; i++) {
				System.out.print("C");
			}
			str = "A";
			// 通知
			c3.signal();
		} finally {
			reentrantLock.unlock();
		}
	}
}
