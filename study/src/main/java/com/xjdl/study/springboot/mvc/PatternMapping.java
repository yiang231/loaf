package com.xjdl.study.springboot.mvc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.function.support.RouterFunctionMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * 路径匹配说明
 * <p>
 * ANT_PATH_MATCHER
 * <p>
 * * 代表任意字符
 * ? 代表单个字符
 * ** 任意数量的目录
 * {} 表示一个命名模式的占位符
 * [] 表示字符合集，如 [a-z]表示所有的小写字母
 * <p>
 * 特殊转义字符
 * * -> \\*,
 * ? -> \\?
 * <p>
 * PATH_PATTERN_PARSER
 * <p>
 * 兼容 ANT_PATH_MATCHER 语法，性能更好
 * ，其中 ** 任意数量的目录匹配 仅允许在末尾使用
 * <p>
 * 切换方式
 * spring.mvc.pathmatch.matching-strategy:
 *
 * @see WebMvcProperties.MatchingStrategy
 */
@Slf4j
@SpringBootTest
@RestController
@RequestMapping("/api")
public class PatternMapping {
	@Value("${server.port}")
	Integer port;
	@Autowired
	RequestMappingHandlerMapping requestMappingHandlerMapping;
	// 函数式 web 映射处理器
	@Autowired
	RouterFunctionMapping routerFunctionMapping;
	public static String IP;

	@GetMapping("/pathMapping")
	@Test
	void pathMapping() {
		Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
		log.warn("{}", "无法获得函数式Web的请求路径");
		try {
			IP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException exception) {
			IP = InetAddress.getLoopbackAddress().getHostAddress();
		}
		handlerMethods.forEach((path, method) -> {
			Object[] array = path.getMethodsCondition().getMethods().toArray();
			StringBuilder requestMethod = new StringBuilder(array.length == 0 ? "GET" : array[0].toString());
			while (requestMethod.length() < 7) {
				requestMethod.append(" ");
			}
			log.info("{} http://{}:{}{}"
					, requestMethod
					, IP, port
					, path.getPathPatternsCondition().getPatterns().toArray()[0]
			);
		});
	}
}
