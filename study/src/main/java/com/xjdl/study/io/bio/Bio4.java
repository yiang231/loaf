package com.xjdl.study.io.bio;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 实现伪异步通信架构
 */
@Slf4j
public class Bio4 {
	public final int PORT = 9999;
	private final int numberOfProcessors = Integer.parseInt(System.getenv("NUMBER_OF_PROCESSORS"));
	private final ExecutorService threadPool = new ThreadPoolExecutor(
			numberOfProcessors >> 1
			, numberOfProcessors
			, 32L, TimeUnit.SECONDS
			, new ArrayBlockingQueue<>(numberOfProcessors << 1)
	);

	@Test
	void server() {
		try {
			ServerSocket ss = new ServerSocket(PORT);
			while (true) {
				Socket s = ss.accept();
				// 提交任务到线程池
				threadPool.execute(() -> {
					try {
						InputStream is = s.getInputStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(is));
						String msg;
						while ((msg = reader.readLine()) != null) {
							log.info("接收到消息: {}", msg);
						}
					} catch (IOException e) {
						log.error(e.getMessage(), e);
					}
				});
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	@Test
	void client() {
		try {
			Socket s = new Socket("localhost", PORT);
			PrintStream ps = new PrintStream(s.getOutputStream());
			Scanner scanner = new Scanner(System.in);
			while (true) {
				String msg = scanner.nextLine();
				log.info("请说: {}", msg);
				ps.println(msg);
				ps.flush();
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
