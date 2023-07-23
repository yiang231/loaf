package com.xjdl.test;

import com.xjdl.context.XjdlApplicationContext;
import com.xjdl.test.config.AppConfig;
import com.xjdl.test.service.BaseService;


public class Main {
	public static void main(String[] args) {
		XjdlApplicationContext applicationContext = new XjdlApplicationContext(AppConfig.class);

		BaseService userService = (BaseService) applicationContext.getBean("userService");
		userService.test();
	}
}
