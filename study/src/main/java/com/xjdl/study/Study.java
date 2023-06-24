package com.xjdl.study;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * 四大资源文件夹访问顺序
 * <p>
 * org.springframework.boot.autoconfigure.web.WebProperties.Resources#CLASSPATH_RESOURCE_LOCATIONS 更新至 2.7.12 版本
 * <p>
 * "classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/"
 */
@ServletComponentScan(basePackages = {
		"com.xjdl.study.javaWeb"
		, "com.xjdl.study.init"
		, "com.xjdl.study.undertow"
})
@SpringBootApplication
//默认访问当前包及其子包下的注解
@EnableTransactionManagement
@MapperScan({
		"com.xjdl.study.myBatisPlus.mapper"
})
@CrossOrigin
@EnableScheduling
@Slf4j
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableCaching
public class Study {
	public static void main(String[] args) {
		// IOC 容器
		ConfigurableApplicationContext ioc = SpringApplication.run(Study.class, args);

		log.info("{}", PACKAGE_COMMON.PACKAGE_INFO_CONST);
		log.info("validate @Scope(\"prototype\") {}", ioc.getBean("miniDog") == ioc.getBean("miniDog"));
	}
}
