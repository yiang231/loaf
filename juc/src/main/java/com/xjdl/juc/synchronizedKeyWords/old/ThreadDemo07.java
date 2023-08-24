package com.xjdl.juc.synchronizedKeyWords.old;

import org.junit.jupiter.api.Test;

/**
 * @ClassName ThreadDemo07
 * @Description 此类用于演示功能
 * @Author Shark
 * @DateTime 2022年07月20日 11时08分11秒
 * @Version 1.0
 * <p>
 * 同步方法
 * 含义:
 * 被synchronized关键字修饰的成员方法
 * 特点:
 * 1.同步方法是针对同步代码块的简化
 * 2.多条线程执行程序,遇到同步方法的调用时,同步方法只能被一条线程进行调用,其它线程需要在方法调用外进行阻塞,等待同步方法被调用完毕,其它线程对象就可以抢夺同步方法的调用
 * 前提:
 * 当多条线程处理同一资源数据时,且多条线程执行线程动作相同的情况
 * 格式:
 * private synchronized 返回类型 方法名 () {}
 */
public class ThreadDemo07 {
	@Test
	void test() throws InterruptedException {
		//创建卖票的执行动作对象
		Ticket ticket = new Ticket();
		//创建三条线程进行卖票
		Thread t1 = new Thread(ticket, "窗口1");
		Thread t2 = new Thread(ticket, "窗口2");
		Thread t3 = new Thread(ticket, "窗口3");
		//开启线程
		t1.start();
		t2.start();
		t3.start();

		Thread.sleep(1000);
	}
}
