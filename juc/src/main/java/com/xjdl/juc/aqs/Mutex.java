package com.xjdl.juc.aqs;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**AQS:
	全称AbstractQueuedSynchronizer 抽象队列同步器 是大部分juc包的基石
	本质: 存放线程对象的一个队列 + state状态位
		名字叫做:CLH 是以3名创始人的首字母来命名的
		整体就是一个抽象的FIFO队列来完成资源获取线程的排队工作，并通过一个int类型变量表示持有锁的状态
	实现思路:
		state状态位的获取(tryAcquire)和释放(tryRelease)
		isHeldExclusively() 当前线程是否持有锁
	ReentrantLock底层实现
	以下三个内部类都继承了AbstractQueuedSynchronizer 重写了tryAcquire 和 tryRelease
	Sync
	该内部类只重写了tryRealease方法,定义一个lock抽象方法,定义了一个nonfairTryAcquire通用方法
	nonfairTryAcquire(int acquires)
		1.获取当前线程对象,并获取了AQS中的状态位state值
		2,如果state=0 cas操作将state设置为acquire,并记录当前线程获取到锁
		3.如果state!=0 判断当前线程是否是有锁线程,如果是拥有锁的线程,则执行重入操作(state+acquire)
	tryRelease(int releases)
		1,获取state,执行state-acquire操作
		2,判断当前线程是否是持有当前锁的线程,如果不是,说明你在准备释放别人的锁,抛出异常信息
		3,判断state是否为0,如果为了,直接释放
		4,如果state不为0,表示当前正处在可重入状态,这把第一步的state值更新一下即可
	NonfairSync
		tryAcquire(int acquires) : 其实就是调用了他的父类NonfairSync的nonfairTryAcquire
		lock()方法
		1,线程先尝试获取锁,如果获取锁成功,就占用锁
		2,获取锁失败,执行acquire方法
			2.1 走tryAcquire获取锁
			2.2 获取锁失败,把当前线程封装为node节点保存调用addwaiter()方法存入CLH队列
			2.3,将node节点放到tail后面
			2.4,中断当前线程
	FairSync
		tryAcquire(int acquires)
		1,获取当前线程对象和state状态位的值
		2,如果state=0时候
			2.1判断等待队列,如果head=tail 说明只有一个线程获取锁,直接返回结果接口,表示获取锁成功
			2.2如果head!=tail 获取下一个要获取锁的节点currentNode 如果该对象为空,则表示获取锁失败
			2.3如果currentNode不为空,并且currentNode的线程等于当前线程,则表示获取锁成功
			2.4如果currentNode不为空,并且currentNode的线程不等于当前线程,则获取锁失败
		3,state!=0,判断当前线程是否是有锁线程,如果是有锁线程,值执行重入操作(state+acquire)

 @see AbstractQueuedSynchronizer
*/
// AQS源码提供的锁案例
public class Mutex implements Lock, Serializable {

	// The sync object does all the hard work. We just forward to it.
	private final Sync sync = new Sync();

	public void lock() {
		sync.acquire(1);
	}

	public boolean tryLock() {
		return sync.tryAcquire(1);
	}

	public void unlock() {
		sync.release(1);
	}

	public Condition newCondition() {
		return sync.newCondition();
	}

	public boolean isLocked() {
		return sync.isHeldExclusively();
	}

	public boolean hasQueuedThreads() {
		return sync.hasQueuedThreads();
	}

	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	public boolean tryLock(long timeout, TimeUnit unit)
			throws InterruptedException {
		return sync.tryAcquireNanos(1, unit.toNanos(timeout));
	}

	// Our internal helper class
	private static class Sync extends AbstractQueuedSynchronizer {
		// Reports whether in locked state
		protected boolean isHeldExclusively() {
			return getState() == 1;
		}

		// Acquires the lock if state is zero
		public boolean tryAcquire(int acquires) {
			assert acquires == 1; // Otherwise unused
			if (compareAndSetState(0, 1)) {
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}

		// Releases the lock by setting state to zero
		protected boolean tryRelease(int releases) {
			assert releases == 1; // Otherwise unused
			if (getState() == 0) throw new IllegalMonitorStateException();
			setExclusiveOwnerThread(null);
			setState(0);
			return true;
		}

		// Provides a Condition
		Condition newCondition() {
			return new ConditionObject();
		}

		// Deserializes properly
		private void readObject(ObjectInputStream s)
				throws IOException, ClassNotFoundException {
			s.defaultReadObject();
			setState(0); // reset to unlocked state
		}
	}
}
