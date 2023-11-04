package com.xjdl.app;

import com.xjdl.app.config.AppConfig;
import com.xjdl.app.service.BaseService;
import com.xjdl.framework.context.support.ClassPathApplicationContext;
import org.junit.jupiter.api.Test;

class MainTest {

	@Test
	void mainTest() throws Exception {
		ClassPathApplicationContext classPathApplicationContext = new ClassPathApplicationContext(AppConfig.class);
		classPathApplicationContext.refresh();

		BaseService adapterService = (BaseService) classPathApplicationContext.getComponent("adapterService");
		adapterService.test();
	}
}
