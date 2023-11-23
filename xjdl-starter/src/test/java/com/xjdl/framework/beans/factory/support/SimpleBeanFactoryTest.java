package com.xjdl.framework.beans.factory.support;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SimpleBeanFactoryTest {
	private BeanFactory beanFactory;

	@BeforeAll
	void beforeAll() {
		beanFactory = new BeanFactory();
	}

	@BeforeEach
	public void setUp() {
		log.info("{}", beanFactory.getBeanMap());
	}

	@AfterEach
	public void tearDown() {
		log.info("{}", beanFactory.getBeanMap());
	}

	@Test
	@Order(1)
	void registerBean() {
		beanFactory.registerBean("person", new Person());
	}

	@Test
	@Order(2)
	void getBean() {
		Person person = (Person) beanFactory.getBean("person");
		Assertions.assertNotNull(person);
	}

	class Person {
		@Override
		public String toString() {
			return "person_value";
		}
	}

	/**
	 * 简单的 Bean 容器
	 */
	public class BeanFactory {
		private final Map<String, Object> beanMap = new ConcurrentHashMap<>();

		public void registerBean(String name, Object bean) {
			beanMap.put(name, bean);
		}

		public Map<String, Object> getBeanMap() {
			return beanMap;
		}

		public Object getBean(String name) {
			return beanMap.get(name);
		}
	}
}
