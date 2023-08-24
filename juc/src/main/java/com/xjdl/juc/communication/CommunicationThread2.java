package com.xjdl.juc.communication;

import org.junit.jupiter.api.Test;

// 线程有序通信
public class CommunicationThread2 {
	@Test
	void test() {
		MyResource2 myResource2 = new MyResource2();
		new Thread(() -> {
			try {
				myResource2.printA5();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		new Thread(() -> {
			try {
				myResource2.printB10();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		new Thread(() -> {
			try {
				myResource2.printC15();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
}

