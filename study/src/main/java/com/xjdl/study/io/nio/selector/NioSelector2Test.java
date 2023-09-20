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
public class NioSelector2Test {
	public static Selector selector;

	static {
		try {
			selector = Selector.open();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	public final int BUFFER_SIZE = 8192;
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

	/**
	 * 在Java NIO中，`Selector` 是用于监视多个通道的一个对象。通过 `Selector` ，可以注册一个或多个通道，并通过调用 `select()` 方法来检查这些通道是否可读、可写或有其他感兴趣的事件。
	 * <p>
	 * `Selector` 对象具有以下两个相关的方法：
	 * <p>
	 * 1. `selectedKeys()`: 这个方法返回一个包含已选择的键集合的 `Set` 对象。在调用 `select()` 方法之后，可以通过 `selectedKeys()` 方法获取到已准备就绪的通道的选择键集合。每个选择键表示一个特定的通道与一组感兴趣的事件的关联。可以通过遍历选择键集合来处理已准备就绪的通道。
	 * <p>
	 * 2. `keys()`: 这个方法返回一个包含所有已注册通道的键集合的 `Set` 对象。通过 `keys()` 方法可以获取到当前 `Selector` 对象所持有的所有选择键集合，而不仅仅是已经准备就绪的键。这包括注册了但尚未就绪的通道的键。
	 * <p>
	 * 总结一下区别：
	 * <p>
	 * - `selectedKeys()` 方法返回的是已准备就绪的通道的选择键集合，用于处理已经就绪的通道。
	 * - `keys()` 方法返回的是所有已注册通道的键集合，包括已注册但尚未就绪的通道。
	 * <p>
	 * 通常，在处理已准备就绪的通道时，会使用 `selectedKeys()` 方法获取选择键集合，并进行相应的处理操作。处理完成后，需要通过调用 `selectedKeys().remove(key)` 方法显式地移除已处理的键。
	 * <p>
	 * 注意，`selectedKeys()` 方法返回的选择键集合并不会自动清除。每次调用 `select()` 方法后，需要手动清理已处理的选择键集合，否则下一次 `select()` 方法可能仍会返回之前已处理过的选择键。
	 */
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
