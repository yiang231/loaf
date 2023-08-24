package com.xjdl.study.io.bio;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * 任意类型文件上传下载
 */
@Slf4j
public class Bio5 {
	public final int BUFFER_SIZE = 8192;
	public final int PORT = 9999;
	/**
	 * 文件后缀名
	 */
	public String EXT = "";
	/**
	 * 源文件路径以及文件名【不包括后缀名】
	 */
	public String IN_FILE_PATH = "";
	/**
	 * 目标文件路径【最后带\\或者/】
	 */
	public String OUT_FILE_PATH = "";

	@Test
	void server() {
		try {
			ServerSocket ss = new ServerSocket(PORT);
			while (true) {
				log.info("{}", "connect socket.");
				Socket s = ss.accept();
				new Thread(() -> {
					try {
						DataInputStream dis = new DataInputStream(s.getInputStream());
						String ext = dis.readUTF();
						OutputStream fos = Files.newOutputStream(Paths.get(OUT_FILE_PATH + UUID.randomUUID() + ext));
						int length;
						byte[] bytes = new byte[BUFFER_SIZE];
						while ((length = dis.read(bytes)) > 0) {
							fos.write(bytes, 0, length);
						}
						fos.close();
						dis.close();
					} catch (IOException e) {
						log.error(e.getMessage(), e);
					}
				}).start();
				log.info("{}", "file received.");
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	@Test
	void client() {
		try (InputStream fis = Files.newInputStream(Paths.get(IN_FILE_PATH + EXT))) {
			Socket s = new Socket("localhost", PORT);
			log.info("{}", "create socket.");
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeUTF(EXT);
			int length;
			byte[] bytes = new byte[BUFFER_SIZE];
			while ((length = fis.read(bytes)) > 0) {
				dos.write(bytes, 0, length);
			}
			dos.flush();
			// 发送完毕主动关闭，否则服务端一直等待直到异常
			s.shutdownOutput();
			log.info("{}", "file send.");
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
