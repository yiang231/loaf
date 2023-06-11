package com.xjdl.study.springboot.value;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * application-myconfig.yml 自定义配置读取
 */
@Data
@Slf4j
//@Component
@ConfigurationProperties(prefix = "newperson") // 需要配合 @Component注解 或者 @EnableConfigurationProperties注解 使用
public class NewPerson {
	String name;
	Integer age;
	Child child;
	List<Dog> dog;
	Map<String, Cat> cat;
}

@Data
class Child {
	String name;
	Integer age;
	List<String> desc;
}

@Data
class Dog {
	String name;
	Integer age;
}

@Data
class Cat {
	String name = "tom";
	Integer age = 6;
}
