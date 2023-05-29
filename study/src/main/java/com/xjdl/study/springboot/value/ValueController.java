package com.xjdl.study.springboot.value;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@SpringBootTest
public class ValueController {
	@Resource
	ValueDemo valueDemo;
	/**
	 * 属性的全路径匹配
	 */
	@Value("${server.port}")
	private String port;

	@GetMapping("/value")
	public String value() {
		log.info("{}", valueDemo);
		log.info(port);
		return port;
	}

	@Resource
	RemoteProperties remoteProperties;

	@Test
	void remoteProperties() {
		log.info(remoteProperties.getTestName());
		log.info(remoteProperties.getTestPassword());
		log.info(remoteProperties.getTestValue());
	}

	@Resource
	RemoteYaml remoteYaml;

	@Test
	void remoteYaml() {
		log.info(remoteYaml.getTestName());
		log.info(remoteYaml.getTestPassword());
		log.info(remoteYaml.getTestValue());
	}
}
