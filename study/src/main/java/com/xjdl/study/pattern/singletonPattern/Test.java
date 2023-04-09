package com.xjdl.study.pattern.singletonPattern;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

public class Test {
	public static void main(String[] args) {
		Singleton4 instance = Singleton4.getInstance();
		Singleton4 instance1 = Singleton4.getInstance();
		Singleton4 instance2 = Singleton4.getInstance();
		Singleton4 instance3 = Singleton4.getInstance();

		System.out.println("instance = " + instance);
		System.out.println("instance1 = " + instance1);
		System.out.println("instance2 = " + instance2);
		System.out.println("instance3 = " + instance3);

		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return null;
			}
		});
	}
}
