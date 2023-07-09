package com.xjdl.study.springboot.applicationContextInitializer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 感知 IOC 容器初始化
 *
 * @see SpringApplicationRunListener#contextPrepared(ConfigurableApplicationContext) contextPrepared 之前执行
 * @see SpringApplication#getSpringFactoriesInstances(Class)
 */
@Slf4j
public class MyApplicationContextInitializer implements ApplicationContextInitializer {
	/**
	 * 引导阶段开始
	 */
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		log.info("{}", "com.xjdl.study.springboot.applicationContextInitializer.MyApplicationContextInitializer.initialize");
	}
}
