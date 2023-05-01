package com.xjdl.study.scheduleTask;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程等待实现定时任务
 */
@Slf4j
public class ThreadWaitTask {
	public static void main(String[] args) {
		new Thread(() -> {
			while (true) {
				try {
					log.info("线程等待实现定时任务");
					Thread.sleep(1000L);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}).start();
	}
}
