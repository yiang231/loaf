package com.xjdl.framework.beans.factory.config;

import com.xjdl.framework.beans.factory.ListableBeanFactory;
import com.xjdl.framework.beans.factory.NoSuchBeanDefinitionException;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
    BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;
}
