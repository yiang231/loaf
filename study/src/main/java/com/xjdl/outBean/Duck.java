package com.xjdl.outBean;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@Data
public class Duck {
	String name = "鼠鼠我鸭";
	Integer age;

	// 条件注解，什么时候注入Bean
	@Bean
	@ConditionalOnMissingBean
	public Duck duck() {
		return new Duck();
	}
}
