package com.xjdl.app;

import com.xjdl.app.config.AppConfig;
import com.xjdl.app.service.BaseService;
import com.xjdl.framework.context.XjdlApplicationContext;
import org.junit.jupiter.api.Test;

class MainTest {

	@Test
	void main() {
		XjdlApplicationContext applicationContext = new XjdlApplicationContext(AppConfig.class);

		BaseService userService = (BaseService) applicationContext.getBean("userService");
		userService.test();
	}
}
