package com.xjdl.framework.context.support;

import com.xjdl.app.config.CustomBeanPostProcessor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApplicationContextAwareProcessorTest {
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
	void testApplicationContextAwareProcessor() {
		CustomBeanPostProcessor customBeanPostProcessor = (CustomBeanPostProcessor) applicationContext.getBean("customBeanPostProcessor");

		Assertions.assertNotNull(customBeanPostProcessor.getApplicationContext());
		Assertions.assertNotNull(customBeanPostProcessor.getApplicationStartup());
		Assertions.assertNotNull(customBeanPostProcessor.getResourceLoader());
	}
}
