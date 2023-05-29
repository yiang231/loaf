package com.xjdl.study.springboot.value;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 读取自定义yaml配置文件
 */
@Configuration
@ConfigurationProperties(prefix = "remoteyaml.person", ignoreInvalidFields = false)
@PropertySource(value = "classpath:remote.yaml", ignoreResourceNotFound = false)
@Data
@Component
public class RemoteYaml {
	@Value("${testname}")
	private String testName;
	@Value("${testpassword}")
	private String testPassword;
	@Value("${testvalue}")
	private String testValue;
}
