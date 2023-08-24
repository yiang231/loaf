package com.xjdl.juc.synchronizedKeyWords.old;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName Ticket
 * @Description 此类用于演示功能
 * @Author Shark
 * @DateTime 2022年07月20日 09时35分21秒
 * @Version 1.0
 */
@Slf4j
public class Ticket implements Runnable {
	//声明并初始化票的剩余总张数
	private int ticket = 100;

	@Override
	public void run() {
		while (ticket > 0) {
			method();
		}
	}

	private synchronized void method() {
		if (ticket > 0) {
			//模拟实际开发中执行N行代码所消耗的时间
//			try {
//				Thread.sleep(50);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			//获取当前执行线程对象的名字
			String name = Thread.currentThread().getName();
			//线程1 线程2 线程3
			//开始卖票
			log.info("{} 卖了一张票,目前票数剩余 {} 张", name, --ticket);
//		}
		}
	}
}
