package com.xjdl.study.springboot.value;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 属性前缀匹配
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class ValueDemo {
    public String driverClassName;
    public String url;
}
