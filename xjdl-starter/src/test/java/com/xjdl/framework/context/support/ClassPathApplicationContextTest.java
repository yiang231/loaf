package com.xjdl.framework.context.support;

import com.xjdl.app.config.AppConfig;
import com.xjdl.app.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ClassPathApplicationContextTest {

	@Test
	void refresh() throws Exception {
		ClassPathApplicationContext classPathApplicationContext = new ClassPathApplicationContext(AppConfig.class);
		classPathApplicationContext.refresh();

		BaseService adapterService = (BaseService) classPathApplicationContext.getComponent("adapterService");
		adapterService.test();
	}
}
