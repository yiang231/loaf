package com.xjdl.study.springboot.mvc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

@Slf4j
@SpringBootTest
@RestController
@RequestMapping("/api")
public class PatternMapping {
	@Value("${server.port}")
	Integer port;
	@Autowired
	RequestMappingHandlerMapping requestMappingHandlerMapping;

	@GetMapping("/pathMapping")
	@Test
	void pathMapping() {
		Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
		handlerMethods.forEach((path, method) -> {
			Object[] array = path.getMethodsCondition().getMethods().toArray();
			if (array.length == 0) {
				log.info("{} http://{}:{}{}", "GET", "127.0.0.1", port, path.getPathPatternsCondition().getPatterns().toArray()[0]);
			} else {
				log.info("{} http://{}:{}{}", array[0], "127.0.0.1", port, path.getPathPatternsCondition().getPatterns().toArray()[0]);
			}
		});
	}
}
