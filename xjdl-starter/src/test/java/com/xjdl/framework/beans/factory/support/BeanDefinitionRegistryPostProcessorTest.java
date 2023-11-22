package com.xjdl.framework.beans.factory.support;

import com.xjdl.app.config.CustomBeanDefinitionRegistryPostProcessor;
import com.xjdl.app.service.HelloWorldService;
import com.xjdl.app.service.OutputService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BeanDefinitionRegistryPostProcessorTest {
    @Test
    void valid() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinitionRegistryPostProcessor postProcessor = new CustomBeanDefinitionRegistryPostProcessor();
        postProcessor.postProcessBeanFactory(beanFactory);
        postProcessor.postProcessBeanDefinitionRegistry(beanFactory);

        OutputService outPutService = (OutputService) beanFactory.getBean("outPutService");
        Assertions.assertNotNull(outPutService, "直接注册的单例Bean");

        beanFactory.getBean("helloWorldService");
        HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
        Assertions.assertNotNull(helloWorldService, "直接注册的BeanDefinition");
    }
}