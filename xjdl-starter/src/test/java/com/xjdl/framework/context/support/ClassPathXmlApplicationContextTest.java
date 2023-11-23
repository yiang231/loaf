package com.xjdl.framework.context.support;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class ClassPathXmlApplicationContextTest {
	@Test
	void testClassPathXmlApplicationContext() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
			applicationContext.getBean(beanDefinitionName);
		}
		Assertions.assertNotNull(applicationContext);
	}
}
