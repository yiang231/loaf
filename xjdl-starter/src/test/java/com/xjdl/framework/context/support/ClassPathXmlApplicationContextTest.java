package com.xjdl.framework.context.support;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClassPathXmlApplicationContextTest {
	private ClassPathXmlApplicationContext applicationContext;

	@BeforeAll
	void beforeAll() {
		applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	@Test
	void testClassPathXmlApplicationContext() {
		for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
			applicationContext.getBean(beanDefinitionName);
		}
		Assertions.assertNotNull(applicationContext);
	}

	@Test
	void testDisposableBeanAndInitializingBeanWithShutdownHook() {
		for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
			applicationContext.getBean(beanDefinitionName);
		}
		applicationContext.registerShutdownHook();
		applicationContext.close();
	}
}
