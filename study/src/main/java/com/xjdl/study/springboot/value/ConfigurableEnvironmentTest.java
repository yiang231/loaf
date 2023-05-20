package com.xjdl.study.springboot.value;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.annotation.Resource;

/**
 * 获取配置参数
 */
@SpringBootTest
@Slf4j
public class ConfigurableEnvironmentTest {
	@Resource
	ConfigurableEnvironment configurableEnvironment;

	@Test
	public void property() {
		configurableEnvironment.getSystemEnvironment().forEach((s, o) -> log.info("getSystemEnvironment {} = {}", s, o));
		configurableEnvironment.getSystemProperties().forEach((s, o) -> log.info("getSystemProperties {} = {}", s, o));
		configurableEnvironment.getPropertySources().stream().forEach(propertySource -> log.info("getPropertySources {}", propertySource));
		log.info(configurableEnvironment.getProperty("spring.boot.admin.client.url"));
		log.info(configurableEnvironment.getProperty("mybatis-plus.mapper-locations"));
		log.info(configurableEnvironment.getProperty("user.name"));
	}

}
