package com.xjdl.juc.volatileKeyWord;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

// 验证可见性
public class volatileDemo1 {
	@Test
	void test() {
		{
			Data data = new Data();
			new Thread(() -> {
				try {
					System.out.println(Thread.currentThread().getName() + "\t 进入线程");
					TimeUnit.SECONDS.sleep(3);
					data.addNum();
					System.out.println(Thread.currentThread().getName() + "\t 当前的数字为" + data.num);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}, "线程1").start();
			while (data.num == 0) {

			}
			System.out.println(Thread.currentThread().getName() + "\t最终的数字为\t" + data.num);
		}
	}
}
