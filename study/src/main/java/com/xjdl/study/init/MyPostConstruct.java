package com.xjdl.study.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 将要执行的方法所在的类交个spring容器扫描(@Component),并且在要执行的方法上添加@PostConstruct注解或者静态代码块执行
 */
@Component
@Slf4j
public class MyPostConstruct {
	@PostConstruct
	public void postConstruct() {
		log.info("{}", "com.xjdl.study.init.MyPostConstruct.postConstruct");
	}
}
