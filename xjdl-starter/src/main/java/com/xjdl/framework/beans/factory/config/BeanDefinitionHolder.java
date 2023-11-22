package com.xjdl.framework.beans.factory.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 解析完 XML 文件，将获得的 BeanDefinition 放入其中，同时连带着名称
 */
@Getter
@AllArgsConstructor
public class BeanDefinitionHolder {
    private final BeanDefinition beanDefinition;

    private final String beanName;

    public BeanDefinitionHolder(BeanDefinitionHolder beanDefinitionHolder) {
        this.beanDefinition = beanDefinitionHolder.getBeanDefinition();
        this.beanName = beanDefinitionHolder.getBeanName();
    }
}
