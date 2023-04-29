package com.xjdl.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 四大资源文件夹访问顺序
 * org.springframework.boot.autoconfigure.web.WebProperties.Resources 2.7.10版本
 * private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
    "classpath:/resources/", "classpath:/static/", "classpath:/public/" };
 * Locations of static resources. Defaults to classpath:[/META-INF/resources/,
 * /resources/, /static/, /public/].
 */
@ServletComponentScan(basePackages = "com.xjdl.study.javaWeb")
@SpringBootApplication
//默认访问当前包及其子包下的注解
@EnableTransactionManagement
public class Study {
    public static void main(String[] args) {
        SpringApplication.run(Study.class, args);
    }

}
