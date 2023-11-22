package com.xjdl.framework.beans.factory;

import com.xjdl.framework.beans.BeansException;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory {
    String[] getBeanDefinitionNames();

    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;
}
