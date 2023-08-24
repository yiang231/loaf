package com.xjdl.juc.Callable;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

// 使用Callable创建线程
public class CallableDemo implements Callable<String> {
	@Override
	public String call() throws Exception { // 1、会抛出【声明】异常
		System.out.println("Callable测试");
		TimeUnit.SECONDS.sleep(3);
//		throw new RuntimeException();
		return "CallableDemo"; // 2、指定返回值
	}
}
