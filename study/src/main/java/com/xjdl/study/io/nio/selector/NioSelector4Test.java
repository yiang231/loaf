package com.xjdl.study.io.nio.selector;

import com.xjdl.study.io.nio.util.ByteBufUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程优化
 */
@Slf4j
public class NioSelector4Test {
	public final int PORT = 9999;

	@Test
	void server() {
		try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
			Thread.currentThread().setName("boss");
			log.info("{}", "server start");
			ssc.configureBlocking(false);

			Selector boss = Selector.open();
			ssc.register(boss, SelectionKey.OP_ACCEPT);
			ssc.bind(new InetSocketAddress(PORT));

			// 动态获取机器可用的核心数
			int processors = Runtime.getRuntime().availableProcessors();
			Worker[] workers = new Worker[processors];
			for (int i = 0; i < workers.length; i++) {
				workers[i] = new Worker("worker_" + i);
			}
			AtomicInteger index = new AtomicInteger();

			while (boss.select() > 0) {
				Iterator<SelectionKey> iter = boss.selectedKeys().iterator();
				while (iter.hasNext()) {
					SelectionKey sk = iter.next();
					iter.remove();
					if (sk.isAcceptable()) {
						SocketChannel sc = ssc.accept();
						sc.configureBlocking(false);
						SocketAddress remoteAddress = sc.getRemoteAddress();
						log.info("connecting {}", remoteAddress);
						log.info("before register {}", remoteAddress);
						// 使用负载均衡算法轮询【Round Robin】 worker
						if (index.get() == processors) {
							index.set(0);
						}
						// 0 % 2 = 0, 1 % 2 = 1, 2 % 2 = 0, 3 % 2 = 1
						workers[index.getAndIncrement() % workers.length].register(sc);
						log.info("after register {}", remoteAddress);
					}
				}
			}
			log.info("{}", "server end");
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	@Test
	void client() {
		try (SocketChannel sc = SocketChannel.open(new InetSocketAddress("localhost", PORT))) {
			Thread.currentThread().setName("client");
			log.info("client start {}", sc.getLocalAddress());
			sc.write(Charset.defaultCharset().encode("Hello World!"));
			log.info("{}", "client end");
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	class Worker implements Runnable {
		private Thread thread;
		private Selector selector;
		private String name;
		private volatile boolean flag = false;
		/**
		 * 使用队列来解决线程间通信问题，将任务作为队列元素进行传递，保证了 SocketChannel 的注册和 worker 的轮询在同一线程执行
		 */
		private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();

		public Worker(String name) {
			this.name = name;
		}

		public void register(SocketChannel sc) {
			try {
				if (!flag) {
					this.selector = Selector.open();
					this.thread = new Thread(this, name);
					thread.start();
					this.flag = true;
				}
				// 往队列中添加任务，此时重写的 run() 已经执行，注册了可读事件，轮询操作造成了阻塞；
				// 或者不用添加队列，注册后直接进行唤醒，也能达到一样的效果
				queue.add(() -> {
					try {
						sc.register(selector, SelectionKey.OP_READ);
					} catch (ClosedChannelException e) {
						throw new RuntimeException(e);
					}
				});
				// 唤醒选择器，结束阻塞
				selector.wakeup();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}

		@Override
		public void run() {
			try {
				// selector 一次只能干一件事，被阻塞了就不能被 SocketChannel 所注册
				// fixme 为何不能这样写？
//				while (selector.select() > 0) {
				while (true) {
					selector.select();
					// 从队列中取出任务并且执行
					Runnable task = queue.poll();
					if (task != null) {
						task.run();
					}

					Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
					while (iter.hasNext()) {
						SelectionKey sk = iter.next();
						iter.remove();
						try {
							if (sk.isReadable()) {
								ByteBuffer buffer = ByteBuffer.allocate(16);
								SocketChannel sc = (SocketChannel) sk.channel();
								sc.configureBlocking(false);
								int read = sc.read(buffer);
								if (read == -1) {
									sk.cancel();
									break;
								}
								buffer.flip();
								ByteBufUtil.debugAll(buffer);
							}
						} catch (IOException e) {
							sk.cancel();
						}
					}
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}
}
