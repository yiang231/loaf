package com.xjdl.framework.beans.factory.config;

public interface BeanPostProcessor {
    Object postProcessBeforeInitialize(String name, Object bean);

    Object postProcessAfterInitialize(String name, Object bean);
}
