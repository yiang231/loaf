package com.xjdl.study.springboot.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 统一管理Spring MVC配置
 * <p>
 * 两种MVC的配置方式
 * 1. 实现WebMvcConfigurer，并且加上@Configuration注解表明这是一个配置类并且放入容器中
 * 2. @Bean往容器中放一个 WebMvcConfigurer ，自定义规则重写默认方法
 * 3. 直接继承或实现某一个组件，并且加上@Configuration注解
 * 4. 方法一和方法三同时使用，会增加两个配置类，且三的优先级高于一
 * <p>
 * 拦截器配置类
 */
@Configuration
//@EnableWebMvc //全面接管 Spring MVC
public class MvcConfig implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 保留原有的规则
		WebMvcConfigurer.super.addInterceptors(registry);
		// 自定义规则 排除所有路径过滤规则
		registry.addInterceptor(new MyHandlerInterceptor())
				.addPathPatterns()
				.excludePathPatterns("/**");
	}

//    @Bean
//    public WebMvcConfigurer webMvcConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addInterceptors(InterceptorRegistry registry) {
//                WebMvcConfigurer.super.addInterceptors(registry);
//                registry.addInterceptor(myInterceptor)
//                        .addPathPatterns()
//                        .excludePathPatterns("/**");
//            }
//        };
//    }

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		WebMvcConfigurer.super.configureMessageConverters(converters);
		converters.add(new MyYamlHttpMessageConverter());
	}
}
