package com.xjdl.juc.juc_3utils;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

// 循环栅栏
public class CyclicBarrierDemo {
	@Test
	void test() {
		// parties指定参与相互等待的线程数，barrierAction一个可选的Runnable命令，该命令只在每个屏障点运行一次，可以在执行后续业务之前共享状态。
		// 该操作由最后一个进入屏障点的线程执行。
		CyclicBarrier cyclicBarrier = new CyclicBarrier(6, () -> {
			System.out.println("人齐了才开会");
		});
		for (int i = 0; i < 18; i++) {
			new Thread(() -> {
				// 由最后到达await方法的线程执行打印的
				System.out.println("第" + Thread.currentThread().getName() + "人到了");
				try {
					cyclicBarrier.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			}, String.valueOf(i)).start();
		}
		//第0人到了
		//第4人到了
		//第3人到了
		//第2人到了
		//第1人到了
		//第6人到了
		//人齐了才开会
		//第5人到了
		//第8人到了
		//第7人到了
		//第9人到了
		//第10人到了
		//第11人到了
		//人齐了才开会
		//第14人到了
		//第17人到了
		//第12人到了
		//第16人到了
		//第15人到了
		//第13人到了
		//人齐了才开会
	}
}
