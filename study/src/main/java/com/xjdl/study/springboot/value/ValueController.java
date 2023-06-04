package com.xjdl.study.springboot.value;

import com.xjdl.study.exception.globalException.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@SpringBootTest
@RequestMapping("/value")
public class ValueController {
	@Resource
	ValueDemo valueDemo;
	@Resource
	NewPerson newPerson;
	@Resource
	Dog dog;
	@Resource
	RemoteProperties remoteProperties;
	@Resource
	RemoteYaml remoteYaml;
	@Resource
	Cat cat;
	/**
	 * 属性的全路径匹配
	 */
	@Value("${server.port}")
	private String port;

	@GetMapping("/port")
	public String value() {
		log.info("{}", valueDemo);
		log.info("{}", port);
		return port;
	}

	@GetMapping("/newperson")
	public ResultResponse newperson() {
		return ResultResponse.success(newPerson);
	}

	@GetMapping("/dog")
	public ResultResponse dog() {
		return ResultResponse.success(dog);
	}

	@Test
	void remoteProperties() {
		log.info(remoteProperties.getTestName());
		log.info(remoteProperties.getTestPassword());
		log.info(remoteProperties.getTestValue());
	}

	@Test
	void remoteYaml() {
		log.info(remoteYaml.getTestName());
		log.info(remoteYaml.getTestPassword());
		log.info(remoteYaml.getTestValue());
	}

	@GetMapping("/cat")
	public ResultResponse cat() {
		return ResultResponse.success(cat);
	}
}
