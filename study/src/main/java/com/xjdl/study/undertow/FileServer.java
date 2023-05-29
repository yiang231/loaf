package com.xjdl.study.undertow;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.resource.PathResourceManager;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;

/**
 * 使用Undertow做文件系统服务器
 * Undertow 是基于java nio的web服务器，应用比较广泛，内置提供的PathResourceManager，可以用来直接访问文件系统；如果你有文件需要对外提供访问，除了ftp,nginx等，undertow 也是一个不错的选择，作为java开发，服务搭建非常简便。
 */
@Slf4j
//@Component
@Data
public class FileServer implements InitializingBean {
	private Undertow fileServer;
	private int port = 8888;
	private String host = "127.0.0.1";

	/**
	 * 获取项目文件
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			File file = new File(System.getProperty("user.dir"));
			fileServer = Undertow
					.builder()
					.addHttpListener(port, host)
					.setHandler(
							Handlers.resource(
											new PathResourceManager(file.toPath(), 100)
									)
									.setDirectoryListingEnabled(true)
					)
					.build();
			fileServer.start();
			fileServer.getListenerInfo()
					.forEach(listenerInfo -> log.info("文件系统启动成功，访问地址为 {}:/{}", listenerInfo.getProtcol(), listenerInfo.getAddress()));

//		Undertow pageServer = Undertow.builder()
//				.addHttpListener(7006, "127.0.0.1")
//				.setHandler(new HttpHandler() { // 设置HttpHandler回调方法
//					@Override
//					public void handleRequest(final HttpServerExchange exchange) {
//						log.info("hit {}", exchange.getRequestPath());
//						exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
//						exchange.getResponseSender().send(exchange.getRequestPath());
//					}
//				}).build();
//		pageServer.start();
//		log.info("页面系统启动成功");
		} catch (Exception e) {
			fileServer.stop();
			log.error("文件系统启动失败 {}", e.getMessage());
		}
	}
}
