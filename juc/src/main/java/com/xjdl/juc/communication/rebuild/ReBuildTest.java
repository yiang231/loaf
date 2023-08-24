package com.xjdl.juc.communication.rebuild;

import org.junit.jupiter.api.Test;

// 使用可重入锁
public class ReBuildTest {
	private static final ReBuildResource rebuild = new ReBuildResource();

	public void print(String str) {
		new Thread(() -> {
			for (int i = 0; i < 30; i++) {
				rebuild.printStr(str);
			}
		}).start();
	}

	@Test
	void test() throws InterruptedException {
		print("Q");
		print("W");
		print("E");
		print("R");
		Thread.sleep(1000);
	}
}
