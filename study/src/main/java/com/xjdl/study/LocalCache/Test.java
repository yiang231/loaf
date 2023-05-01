package com.xjdl.study.LocalCache;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Test {
	public static void main(String[] args) {
		try {
			LocalCache localCache = LocalCache.getInstance();
			localCache.put("第一次", "随便", 1, TimeUnit.SECONDS);

			log.info(localCache.get("第一次").getValue());
			long first = System.currentTimeMillis();
			log.info("{}", first);
			Thread.sleep(1020);
			log.info(localCache.get("第一次").getValue());
			long second = System.currentTimeMillis();
			log.info("{}", second - first);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
	}
}
