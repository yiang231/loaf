package com.xjdl.juc.lock_8;

import java.util.concurrent.TimeUnit;

public class Phone {
	public static synchronized void sendMsg() throws InterruptedException {
		TimeUnit.SECONDS.sleep(4);
		System.out.println(Thread.currentThread().getName() + " \t 发短信...");
	}

	public synchronized void sendEmail() {
		System.out.println(Thread.currentThread().getName() + " \t 发邮件...");
	}

	public void sayHello() {
		System.out.println(Thread.currentThread().getName() + " \t say hello...");
	}
}
