package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.factory.config.BeanDefinition;

/**
 * 注册 BeanDefinition 时生成名称
 */
public interface BeanNameGenerator {
    String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry);
}