package com.xjdl.framework.beans.factory.support;

import com.xjdl.app.service.HelloWorldService;
import com.xjdl.app.service.OutputService;
import com.xjdl.framework.beans.MutablePropertyValues;
import com.xjdl.framework.beans.PropertyValue;
import com.xjdl.framework.beans.PropertyValues;
import com.xjdl.framework.beans.factory.config.BeanDefinition;
import com.xjdl.framework.beans.factory.config.BeanReference;
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

		// 非循环依赖的属性注入
		beanInjectToBean(beanFactory, propertyValues);

		BeanDefinition helloWorldServiceBeanDefinition = new BeanDefinition();
		helloWorldServiceBeanDefinition.setPropertyValues(propertyValues);
		helloWorldServiceBeanDefinition.setBeanClass(HelloWorldService.class);

		beanFactory.registerBeanDefinition("helloWorldService", helloWorldServiceBeanDefinition);

		beanFactory.getBean("helloWorldService");
		HelloWorldService service = (HelloWorldService) beanFactory.getBean("helloWorldService");

		Assertions.assertEquals(service.getText(), "helloWorldService");
		Assertions.assertNotNull(service.getOutputService());
	}

	private void beanInjectToBean(DefaultListableBeanFactory beanFactory, PropertyValues propertyValues) {
		BeanDefinition outServiceBeanDefinition = new BeanDefinition();
		outServiceBeanDefinition.setBeanClass(OutputService.class);
		beanFactory.registerBeanDefinition("outputService", outServiceBeanDefinition);
		PropertyValue outputServicePropertyValue = new PropertyValue("outputService", new BeanReference("outputService"));
		propertyValues.addPropertyValue(outputServicePropertyValue);
	}
}
