package com.xjdl.study.io.nio.selector;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

/**
 * nio 非阻塞通信
 */
@Slf4j
public class NioSelector1Test {
	public final int PORT = 9999;

	@Test
	void server() {
		log.info("server start");
		try {
			// 建立服务端通道接收客户端的连接请求
			ServerSocketChannel ssc = ServerSocketChannel.open();
			// 设置为非阻塞
			ssc.configureBlocking(false);
			// 绑定端口
			ssc.bind(new InetSocketAddress(PORT));
			// 获取选择器
			Selector selector = Selector.open();
			// 服务端通道注册选择器
//			ssc.register(selector, SelectionKey.OP_ACCEPT);
			SelectionKey sscSK = ssc.register(selector, 0, null);
			sscSK.interestOps(SelectionKey.OP_ACCEPT);
			// 使用选择器轮询已经就绪的事件
			while (selector.select() > 0) {
				// 获取所有就绪的事件的迭代器集合，不会主动删除元素
				Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
				while (iterator.hasNext()) {
					log.info("开始一轮处理");
					// 获取一个就绪事件
					SelectionKey selectionKey = iterator.next();
					// 判断事件的类型
					if (selectionKey.isAcceptable()) {
						// 如果是接入的客户端类型，建立连接
						// accept() 阻塞方法
//						SocketChannel sc = ssc.accept();
						// selectionKey.channel() 就是 ServerSocketChannel ssc
						SocketChannel sc = ((ServerSocketChannel) selectionKey.channel()).accept();
						// 切换为非阻塞
						sc.configureBlocking(false);
						// 客户端通道注册选择器，同时将 ByteBuffer 作为附件关联到 SelectionKey
						ByteBuffer buffer = ByteBuffer.allocate(8192);
						SelectionKey register = sc.register(selector, SelectionKey.OP_READ, buffer);
						log.info("建立连接 {}", sc.getRemoteAddress());
					} else if (selectionKey.isReadable()) {
						try {
							// 如果是读事件的客户端类型，可以通过事件反向获取客户端通道
							SocketChannel sc = (SocketChannel) selectionKey.channel();
							// 从 SelectionKey 中 获取关联的附件
							ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
							// 关联新的附件，解决半包粘包消息边界问题时，扩容时会使用
//							selectionKey.attach();
							// 读取数据
							// read() 阻塞方法
							while (true) {
								int read = sc.read(buffer);
								if (read == -1) {
									// 正常断开时会触发读事件，返回 -1，需要取消事件
									selectionKey.cancel();
									break;
								}
								if (read == 0) break;
								buffer.flip();
								log.info("接收到来自 {} 的信息 {}", sc.getRemoteAddress(), new String(buffer.array(), 0, read));
								// 清除数据，为下一次读取做准备
								buffer.clear();
							}
						} catch (IOException e) {
							// 异常断开时会触发读事件，远程主机强迫关闭了一个现有的连接，直接取消事件即可
							selectionKey.cancel();
						}
					}
					// 处理完毕移除事件
					iterator.remove();
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	@Test
	void client() {
		log.info("client start");
		try (SocketChannel sc = SocketChannel.open(new InetSocketAddress("localhost", PORT))) {
			sc.configureBlocking(false);
			ByteBuffer buffer = ByteBuffer.allocate(8192);
			Scanner scanner = new Scanner(System.in);
			while (scanner.hasNext()) {
				String msg = scanner.nextLine();
				log.info("请说: {}", msg);
				buffer.put(msg.getBytes(StandardCharsets.UTF_8));
				buffer.flip();
				sc.write(buffer);
				buffer.clear();
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
