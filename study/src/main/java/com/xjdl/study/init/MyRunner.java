package com.xjdl.study.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 实现ApplicationRunner, CommandLineRunner接口
 * 用于指示bean包含在SpringApplication中时应运行的接口。可以定义多个ApplicationRunner Bean
 * 在同一应用程序上下文中，可以使用有序接口或@order注释对其进行排序。
 * 如果需要访问applicationArguments而不是原始字符串数组，请考虑使用ApplicationRunner。
 */
@Slf4j
@Component
public class MyRunner implements ApplicationRunner, CommandLineRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("{}", "com.xjdl.study.init.MyRunner.run(org.springframework.boot.ApplicationArguments)");
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("{}", "com.xjdl.study.init.MyRunner.run(java.lang.String...)");
	}
}
