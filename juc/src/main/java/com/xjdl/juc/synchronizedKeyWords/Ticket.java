package com.xjdl.juc.synchronizedKeyWords;


import com.xjdl.juc.aqs.Mutex;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Ticket {
	// private ReentrantLock reentrantLock = new ReentrantLock(true); // 测试公平性，默认unfair
//	private ReentrantLock reentrantLock = new ReentrantLock();
	Mutex reentrantLock = new Mutex();
	private int num = 30;

	public void saleTicket() {
		try {
//			boolean b = reentrantLock.tryLock(4, TimeUnit.SECONDS);
//			log.info("限时等待 是否获取到锁 {}", b);
			reentrantLock.lock();
			if (num <= 0) {
				log.info("{}", "票已售罄。。。");
				return;
			}
			log.info("{} 当前正在销售的票号为 {} ,还剩下 {} 张票", Thread.currentThread().getName(), num--, num);
//			check();
//		} catch (InterruptedException e) {
//			throw new RuntimeException(e);
		} finally {
			reentrantLock.unlock();
		}
	}

	/**
	 * 测试 ReentrantLock 可重入性
	 */
	private void check() {
		reentrantLock.lock();
		try {
			log.info("{}", "检查余票。。。");
		} finally {
			reentrantLock.unlock();
		}
	}
}
