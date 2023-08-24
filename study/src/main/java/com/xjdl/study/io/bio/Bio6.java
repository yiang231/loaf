package com.xjdl.study.io.bio;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 端口转发
 */
@Slf4j
public class Bio6 {
	public final int PORT = 9999;
	public List<Socket> sockets = new ArrayList<>();

	@Test
	void server() {
		try {
			ServerSocket ss = new ServerSocket(PORT);
			while (true) {
				Socket s = ss.accept();
				// 连接时添加到集合中
				sockets.add(s);
				new Thread(() -> {
					try (BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
						String msg;
						while ((msg = reader.readLine()) != null) {
							// 推送消息给其他 socket
							pushMsg(s, msg);
						}
					} catch (Exception e) {
						// 断开连接时从集合中移除
						sockets.remove(s);
						log.error(e.getMessage(), e);
					}
				}).start();
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	private void pushMsg(Socket s, String msg) {
		sockets.stream()
				.filter(socket -> !s.equals(socket))
				.forEach(socket -> {
					try {
						// 转发完一个不要关闭
						PrintStream ps = new PrintStream(socket.getOutputStream());
						ps.println(msg);
						ps.flush();
						log.info("push msg [{}] to {}", msg, socket);
					} catch (IOException e) {
						log.error(e.getMessage(), e);
					}
				});
	}

	Runnable sendClient(Socket s) {
		return () -> {
			try {
				PrintStream ps = new PrintStream(s.getOutputStream());
				Scanner scanner = new Scanner(System.in);
				while (true) {
					String msg = scanner.nextLine();
					ps.println(msg);
					ps.flush();
					log.info("send msg ===> {}", msg);
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		};
	}

	Runnable receiveClient(Socket s) {
		return () -> {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
				while (true) {
					String msg;
					while ((msg = reader.readLine()) != null) {
						log.info("received msg ===> {}", msg);
					}
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		};
	}

	/**
	 * 全双工客户端
	 */
	@Test
	void client() {
		try {
			Socket s = new Socket("localhost", PORT);
			Thread sendThread = new Thread(sendClient(s), "sendThread");
			Thread receiveThread = new Thread(receiveClient(s), "receiveThread");
			sendThread.start();
			sendThread.join();
			receiveThread.start();
			receiveThread.join();
		} catch (IOException | InterruptedException e) {
			log.error(e.getMessage(), e);
		}
	}
}
