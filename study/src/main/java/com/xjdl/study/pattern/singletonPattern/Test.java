package com.xjdl.study.pattern.singletonPattern;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

@Slf4j
public class Test {
	public static void main(String[] args) {
		Singleton4 instance = Singleton4.getInstance();
		Singleton4 instance1 = Singleton4.getInstance();
		Singleton4 instance2 = Singleton4.getInstance();
		Singleton4 instance3 = Singleton4.getInstance();

		log.info("instance = {}", instance);
		log.info("instance1 = {}", instance1);
		log.info("instance2 = {}", instance2);
		log.info("instance3 = {}", instance3);

		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return null;
			}
		});
	}
}
