package com.xjdl.framework.beans.factory.support;

import com.xjdl.app.service.HelloWorldService;
import com.xjdl.framework.beans.MutablePropertyValues;
import com.xjdl.framework.beans.PropertyValue;
import com.xjdl.framework.beans.PropertyValues;
import com.xjdl.framework.beans.factory.config.BeanDefinition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AbstractAutowireCapableBeanFactoryTest {
	@Test
	void setBeanFieldValue() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		PropertyValues propertyValues = new MutablePropertyValues();
		PropertyValue text_propertyValue = new PropertyValue("text", "helloWorldService");
		propertyValues.addPropertyValue(text_propertyValue);

		PropertyValue text = propertyValues.getPropertyValue("text");
		Assertions.assertEquals(text.getValue(), "helloWorldService");

		BeanDefinition helloWorldServiceBeanDefinition = new BeanDefinition();
		helloWorldServiceBeanDefinition.setPropertyValues(propertyValues);
		helloWorldServiceBeanDefinition.setBeanClass(HelloWorldService.class);

		beanFactory.registerBeanDefinition("helloWorldService", helloWorldServiceBeanDefinition);

		beanFactory.getBean("helloWorldService");
		HelloWorldService service = (HelloWorldService) beanFactory.getBean("helloWorldService");

		Assertions.assertEquals(service.getText(), "helloWorldService");
	}
}
