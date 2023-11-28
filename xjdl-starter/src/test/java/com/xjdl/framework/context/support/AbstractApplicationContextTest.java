package com.xjdl.framework.context.support;

import com.xjdl.app.event.CustomEvent;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AbstractApplicationContextTest {
	private ClassPathXmlApplicationContext applicationContext;

	@BeforeAll
	void beforeAll() {
		applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	@AfterAll
	void afterAll() {
		applicationContext.close();
	}

	@Test
	void testEvent() {
		CustomEvent customEvent = new CustomEvent(applicationContext);
		applicationContext.publishEvent(customEvent);
	}
}
