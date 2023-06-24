package com.xjdl.study.springboot.function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * 函数式 web
 */
@Configuration(proxyBeanMethods = false)
public class MyRoutingConfiguration {
	private static final RequestPredicate ACCEPT_JSON = RequestPredicates.accept(MediaType.APPLICATION_JSON);

	@Bean
	public RouterFunction<ServerResponse> routerFunction(MyUserHandler userHandler) {
		return RouterFunctions.route()
				.GET("/function/user/{id}", ACCEPT_JSON, userHandler::getUser)
				.GET("/function/users", userHandler::getUsers)
				.POST("/function/user", ACCEPT_JSON, userHandler::saveUser)
				.PUT("/function/user/{id}", ACCEPT_JSON, userHandler::updateUser)
				.DELETE("/function/user/{id}", userHandler::deleteUser)
				.build();
	}
}
