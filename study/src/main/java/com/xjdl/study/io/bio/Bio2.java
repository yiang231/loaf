package com.xjdl.study.io.bio;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * 收发多次消息
 */
@Slf4j
public class Bio2 {

	public final int PORT = 9999;

	@Test
	void server() {
		try {
			ServerSocket ss = new ServerSocket(PORT);
			// 开启监听
			Socket s = ss.accept();
			InputStream is = s.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String msg;
			while ((msg = reader.readLine()) != null) {
				log.info("received message from client ===> {}", msg);
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	@Test
	void client() {
		try {
			Socket s = new Socket("localhost", PORT);
			OutputStream os = s.getOutputStream();
			PrintStream ps = new PrintStream(os);
			Scanner scanner = new Scanner(System.in);
			while (true) {
				String msg = scanner.nextLine();
				log.info("please say something: {}", msg);
				ps.println(msg);
				ps.flush();
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
