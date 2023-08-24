package com.xjdl.juc.communication;

public class MyResource1 {
	public Integer num = 1;

	public synchronized void print0() throws InterruptedException {
		// 判断
		while (num == 1) // 使用while避免虚假唤醒
			wait();
		// 干活
		System.out.print(0);
		num = 1;
		// 通知
		notifyAll();
	}

	public synchronized void print1() throws InterruptedException {
		while (num == 0)
			wait();
		System.out.print(1);
		num = 0;
		notifyAll();
	}
}
