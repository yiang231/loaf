package com.xjdl.juc.communication.rebuild;

import java.util.concurrent.locks.ReentrantLock;

public class ReBuildResource {
	private final ReentrantLock lock = new ReentrantLock(true);

	public void printStr(String str) {
		lock.lock();
		try {
			System.out.print(str);
		} finally {
			lock.unlock();
		}
	}
}
