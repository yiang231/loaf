package com.xjdl.study.rpc.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

/**
 * 全面提供  https 支持
 * SkipSslVerificationHttpRequestFactory#prepareHttpsConnection(HttpsURLConnection)
 */
@Configuration
public class RpcConfig {
//	@Bean
//	public RestTemplate restTemplateByReflect() {
//		ClientHttpRequestFactory factory;
//		try {
//			Class<?> clazz = Class.forName("org.springframework.boot.actuate.autoconfigure.cloudfoundry.servlet.SkipSslVerificationHttpRequestFactory");
//			Constructor<?> constructor = clazz.getDeclaredConstructor();
//			constructor.setAccessible(true);
//			factory = (ClientHttpRequestFactory) constructor.newInstance();
//		} catch (ClassNotFoundException | InstantiationException | NoSuchMethodException | IllegalAccessException |
//				 IllegalArgumentException | InvocationTargetException | SecurityException e) {
//			throw new RuntimeException(e);
//		}
//		return new RestTemplate(factory);
//	}

	/**
	 * autowireCandidate = false
	 * 其不会参与依赖注入
	 */
	@Bean(autowireCandidate = false)
	public RestTemplate restTemplateByNetty() throws SSLException {
		Netty4ClientHttpRequestFactory factory = new Netty4ClientHttpRequestFactory();
		factory.setSslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build());
		factory.setConnectTimeout(15000);
		factory.setReadTimeout(5000);
		return new RestTemplate(factory);
	}

	@Bean
	public RestTemplate restTemplateBySkip(ClientHttpRequestFactory factory) {
		return new RestTemplate(factory);
	}

	/**
	 * WebClient 已提供 https 请求支持
	 */
	@Bean
	public WebClient webClient() throws SSLException {
		SslContext context = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
		HttpClient httpClient = HttpClient.create().secure(sslContextSpec -> sslContextSpec.sslContext(context));
		return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient)).build();
	}

	/**
	 * HttpInterface SpringBoot 3.?
	 */
}
