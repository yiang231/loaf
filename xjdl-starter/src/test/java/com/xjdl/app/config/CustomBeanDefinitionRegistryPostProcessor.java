package com.xjdl.app.config;

import com.xjdl.app.service.HelloWorldService;
import com.xjdl.app.service.OutputService;
import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.config.BeanDefinition;
import com.xjdl.framework.beans.factory.config.ConfigurableListableBeanFactory;
import com.xjdl.framework.beans.factory.support.BeanDefinitionRegistry;
import com.xjdl.framework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

public class CustomBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClass(HelloWorldService.class);

        registry.registerBeanDefinition("helloWorldService", beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        beanFactory.registerSingleton("outPutService", new OutputService());
    }
}
