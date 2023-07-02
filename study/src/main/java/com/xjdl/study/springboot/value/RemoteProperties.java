package com.xjdl.study.springboot.value;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 读取自定义properties配置文件
 */
@Configuration
@ConfigurationProperties(prefix = "remoteproperties.person", ignoreInvalidFields = false)
// 和 spring.config.import 效果相同
//@PropertySource(value = "classpath:remote.properties", ignoreResourceNotFound = false)
@Data
@Component
public class RemoteProperties {
	private String testName;
	private String testPassword;
	private String testValue;
}
