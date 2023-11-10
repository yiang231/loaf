package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.factory.config.BeanDefinition;
import com.xjdl.framework.core.io.ResourceLoader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Map;

@Slf4j
class ClassPathBeanDefinitionReaderTest {

	@Test
	void loadBeanDefinitions() throws Exception {
		ClassPathBeanDefinitionReader classPathBeanDefinitionReader = new ClassPathBeanDefinitionReader(new ResourceLoader());
		classPathBeanDefinitionReader.loadBeanDefinitions("com.xjdl");
		for (Map.Entry<String, BeanDefinition> entry : classPathBeanDefinitionReader.getRegistryBeanDefinition().entrySet()) {
			log.info("{}", entry);
		}
	}
}
