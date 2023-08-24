package com.xjdl.juc.threadPool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {
	private static void cachedThreadPool() {
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool(); // SynchronousQueue
		for (int i = 0; i < 1000000; i++) {
			cachedThreadPool.execute(() -> {
				System.out.println("第\t" + Thread.currentThread().getName() + "\t号线程");
			});
		}
		cachedThreadPool.shutdown();
	}

	private static void singleThreadExecutor() {
		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor(); // LinkedBlockingQueue
		for (int i = 0; i < 10; i++) {
			singleThreadExecutor.execute(() -> {
				System.out.println("第\t" + Thread.currentThread().getName() + "\t号线程");
			});
		}
		singleThreadExecutor.shutdown();
	}

	private static void fixedThreadPool() {
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);// LinkedBlockingQueue
		for (int i = 0; i < 10000000; i++) {
			fixedThreadPool.execute(() -> {
				System.out.println("第\t" + Thread.currentThread().getName() + "\t号线程");
			});
		}
		fixedThreadPool.shutdown();
	}

	/*1,如何科学的设置线程7个参数
	corePoolsize   核心线程数
		开发环境: 核心线程数的设置就相对较为随意 根据个人电脑的配置情况和项目的情况进行判断 eg 5
		生产环境
			cpu密集型 指的就是 该项目所针对的业务只要是科学计算 此时核心线程数 =  cpu核数+1
			IO 密集型 指的就是 该项目所针对的业务只要是进行IO处理 eg 一般的项目 都是此类型
				核心线程数 = cpu核数*10
	maxmumPoolSize 最大线程数 (救急线程)
		开发环境:较为随意
		生产环境:
			至少要大于等于 核心线程数
	keepAliveTime  最大线程过期时间
		根据实际常见来设置,没有明确规范 根据上级领导安排 设置
	Time           过期时间单位
		根据实际常见来设置,没有明确规范 根据上级领导安排 设置
	BlockingQueue  阻塞队列
		ArrayBlockingQueue(5)
			内存连续 查询效率高 由于无法使用内存碎片,导致一定程度的内存浪费
		LinkedBlockingQueue(5)
			内存不连续 增删效率高 查询慢 内存利用率较高
	ThreadFactory  线程工厂
		一般使用默认
	RejectHandler  拒绝策略
		AbortPoilcy 拒绝并抛出异常 默认使用的,也是推荐在生产环境下使用
		discardPoilcy 拒绝但不抛出异常
		discardOldestPoilcy 拒绝并抛弃等待最久的任务
	CallRunsPoilcy: 调用主线程执行任务      开发环境推荐使用该策略*/
	@Test
	void test() {
	/*线程池的优势：线程复用；控制最大并发数；管理线程。
	1. 降低资源消耗。通过重复利用已创建的线程降低线程创建和销毁造成的销耗。
	2. 提高响应速度。当任务到达时，任务可以不需要等待线程创建就能立即执行。
	3. 提高线程的可管理性。线程是稀缺资源，如果无限制的创建，不仅会销耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一的分配，调优和监控。*/
//		fixedThreadPool(); // 指定数目线程
//		singleThreadExecutor(); // 单线程
		cachedThreadPool(); // 自己扩张线程
	}
}
