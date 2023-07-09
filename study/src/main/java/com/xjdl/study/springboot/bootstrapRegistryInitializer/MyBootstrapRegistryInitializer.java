package com.xjdl.study.springboot.bootstrapRegistryInitializer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.BootstrapRegistryInitializer;
import org.springframework.boot.SpringApplication;

/**
 * 感知应用引导阶段开始
 *
 * @see SpringApplication#getSpringFactoriesInstances(Class)
 * @see SpringApplication#createBootstrapContext()
 */
@Slf4j
public class MyBootstrapRegistryInitializer implements BootstrapRegistryInitializer {
	@Override
	public void initialize(BootstrapRegistry registry) {
		log.info("{}", "com.xjdl.study.springboot.bootstrapRegistryInitializer.MyBootstrapRegistryInitializer.initialize");
	}
}
