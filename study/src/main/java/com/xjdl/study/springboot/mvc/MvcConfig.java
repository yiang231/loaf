package com.xjdl.study.springboot.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 拦截器配置类
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
	@Resource
	private MyInterceptor myInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(myInterceptor)
				.addPathPatterns()
				.excludePathPatterns("/**"); // 排除所有路径过滤规则
	}
}