package com.xjdl.study.io.nio.selector;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

/**
 * nio 群聊
 */
@Slf4j
public class NioSelector2 {
	public final int BUFFER_SIZE = 8192;
	public static Selector selector;

	static {
		try {
			selector = Selector.open();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	public final int PORT = 9999;

	@Test
	void server() {
		try {
			Thread.currentThread().setName("server");
			log.info("server start");
			ServerSocketChannel ssc = ServerSocketChannel.open()
					.bind(new InetSocketAddress(PORT));
			ssc.configureBlocking(false);
			ssc.register(selector, SelectionKey.OP_ACCEPT);

			while (selector.select() > 0) {
				Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
				while (iterator.hasNext()) {
					SelectionKey sk = iterator.next();
					if (sk.isAcceptable()) {
						SocketChannel sc = ssc.accept();
						sc.configureBlocking(false);
						sc.register(selector, SelectionKey.OP_READ);
						log.info("用户上线 {}", sc.getRemoteAddress());
					} else if (sk.isReadable()) {
						readClientMsg(sk);
					}
					iterator.remove();
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	private void readClientMsg(SelectionKey sk) {
		SocketChannel sc = null;
		try {
			sc = (SocketChannel) sk.channel();
			ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
			while (sc.read(buffer) != -1) {
				buffer.flip();
				String msg = new String(buffer.array(), 0, buffer.remaining());
				pushClientMsg(msg, sc);
			}
		} catch (IOException e) {
			try {
				log.info("用户离线 {}", sc.getRemoteAddress());
				// 客户端离线，取消事件
				sk.cancel();
				sc.close();
			} catch (IOException ex) {
				log.error(e.getMessage(), e);
			}
		}
	}

	private void pushClientMsg(String msg, SocketChannel sc) {
		// fixme 为什么是 keys()
		selector.keys().stream()
				.filter(key -> {
					SelectableChannel channel = key.channel();
					return !(channel.equals(sc) || channel instanceof ServerSocketChannel);
				})
				.forEach(key -> {
					try {
						ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
						SocketChannel otherSc = (SocketChannel) key.channel();
						otherSc.write(buffer);
						log.info("转发来自 {} 的消息 [{}] 给 {}", sc.getRemoteAddress(), msg, otherSc.getRemoteAddress());
						buffer.clear();
					} catch (IOException e) {
						log.error(e.getMessage(), e);
					}
				});
	}

	@Test
	void client() {
		try {
			Thread.currentThread().setName("clientWrite");
			log.info("client start");
			SocketChannel sc = SocketChannel.open(new InetSocketAddress("localhost", PORT));
			sc.configureBlocking(false);
			sc.register(selector, SelectionKey.OP_READ);
			// 创建线程接收消息
			new Thread(this::readServerMsg, "clientRead").start();
			// 本身可以发送消息
			Scanner scanner = new Scanner(System.in);
			while (scanner.hasNext()) {
				String msg = scanner.nextLine();
				log.info("请说: {}", msg);
				sendClientMsg(msg, sc);
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	private void sendClientMsg(String msg, SocketChannel sc) {
		try {
			ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
			sc.write(buffer);
			buffer.clear();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	private void readServerMsg() {
		try {
			while (selector.select() > 0) {
				Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
				while (iterator.hasNext()) {
					SelectionKey sk = iterator.next();
					if (sk.isReadable()) {
						SocketChannel sc = (SocketChannel) sk.channel();
						sc.configureBlocking(false);
						ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
						int length;
						while ((length = sc.read(buffer)) != -1) {
							buffer.flip();
							log.info("接收到消息: {}", new String(buffer.array(), 0, length));
							buffer.clear();
						}
						sc.close();
						iterator.remove();
					}
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
