package com.xjdl.framework.beans.factory.config;

import lombok.Getter;
import lombok.Setter;

/**
 * 声明依赖另一个 bean 的对象引用
 */
@Getter
@Setter
public class BeanReference {
    private String beanName;
    private Object bean;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }
}
