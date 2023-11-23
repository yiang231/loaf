package com.xjdl.study;

import com.xjdl.test.ioc.bean.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//@SpringBootTest
class StudyTests {
	/**
	 * @see org.springframework.beans.factory.xml.DefaultDocumentLoader#loadDocument(org.xml.sax.InputSource, org.xml.sax.EntityResolver, org.xml.sax.ErrorHandler, int, boolean)
	 * @see ResourceEntityResolver#resolveEntity(String, String)
	 */
	@Test
	void contextLoads() {
		ApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("application.xml");
		Person person = (Person) classPathXmlApplicationContext.getBean("person");
		Assertions.assertNotNull(person);
	}
}