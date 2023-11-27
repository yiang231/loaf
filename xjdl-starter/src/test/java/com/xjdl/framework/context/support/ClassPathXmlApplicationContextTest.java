package com.xjdl.framework.context.support;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
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

	@AfterAll
	void afterAll() {
		applicationContext.close();
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
	}

	/**
	 * 执行此方法之前因为 HelloWorldService 依赖了 OutputService，从而提前实例化了 OutputService，执行了初始化方法
	 */
	@Test
	void testScopePrototype() {
		int j = 2;
		for (int i = 0; i < j; i++) {
			applicationContext.getBean("outputService");
		}
	}
}
