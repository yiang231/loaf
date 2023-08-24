package com.xjdl.juc.communication;

import org.junit.jupiter.api.Test;

public class CommunicationThread1 {
	private static void printNum(MyResource1 myResource1) {
		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				try {
					myResource1.print0();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				try {
					myResource1.print1();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Test
	void test() {
		MyResource1 myResource1 = new MyResource1();
		printNum(myResource1);
		printNum(myResource1);
	}
}

