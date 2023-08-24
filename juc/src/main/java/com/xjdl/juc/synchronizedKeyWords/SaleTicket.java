package com.xjdl.juc.synchronizedKeyWords;

import org.junit.jupiter.api.Test;

/**
 * 多线程方法测试卖票业务
 */
public class SaleTicket {
	@Test
	void test() throws InterruptedException {
		Ticket ticket = new Ticket();
		new Thread(() -> {
			for (int i = 0; i < 30; i++) {
				ticket.saleTicket();
			}
		}, "窗口1").start();
		new Thread(() -> {
			for (int i = 0; i < 30; i++) {
				ticket.saleTicket();
			}
		}, "窗口2").start();
		new Thread(() -> {
			for (int i = 0; i < 30; i++) {
				ticket.saleTicket();
			}
		}, "窗口3").start();

//		Thread.sleep(3000);
	}
}
