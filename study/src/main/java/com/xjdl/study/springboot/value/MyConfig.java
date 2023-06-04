package com.xjdl.study.springboot.value;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

/**
 * 注入Bean
 */
@Slf4j
@SpringBootConfiguration
@Import(com.xjdl.study.springboot.value.Cat.class)
public class MyConfig {
	/**
	 * bean的名称默认是方法名
	 */
	@Bean("miniDog")
	@Scope("prototype")
	public Dog dog() {
		Dog dog = new Dog();
		dog.setName("da");
		dog.setAge(3);
		return dog;
	}
}
