package com.xjdl.study.io.bio;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Slf4j
public class BioUDPTest {
	public final int PORT = 9999;

	@Test
	void server() {
		try (
				DatagramSocket datagramSocket = new DatagramSocket(PORT)
		) {
			log.info("{} start.", datagramSocket.getLocalSocketAddress());
			byte[] bytes = new byte[64 * 1024];
			DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);

			while (true) {
				datagramSocket.receive(datagramPacket);
				String msg = new String(bytes, 0, datagramPacket.getLength());
				log.info("{} ===> {}", datagramPacket.getSocketAddress(), msg);
				if (msg.equals("esc")) {
					log.info("{}", "close server.");
					break;
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	@Test
	void client() {
		try (
				DatagramSocket datagramSocket = new DatagramSocket();
		) {
			log.info("{} connect.", datagramSocket.getLocalSocketAddress());
			Scanner sc = new Scanner(System.in);
			while (true) {
				String msg = sc.nextLine();
				log.info("say： {}", msg);
				if (msg.equals("exit")) {
					log.info("{}", "exit client.");
					break;
				}
				byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
				DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, new InetSocketAddress(InetAddress.getLoopbackAddress(), PORT));
				datagramSocket.send(datagramPacket);
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
