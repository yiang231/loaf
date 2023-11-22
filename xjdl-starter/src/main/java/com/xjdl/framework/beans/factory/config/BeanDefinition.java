package com.xjdl.framework.beans.factory.config;

import com.xjdl.framework.beans.MutablePropertyValues;
import com.xjdl.framework.beans.PropertyValues;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean 的信息定义
 */
@Getter
@Setter
public class BeanDefinition {
    public static final String SCOPE_DEFAULT = "";
    private String initMethodName;
    private String destroyMethodName;
    private Boolean lazyInit;
    private String scope = SCOPE_DEFAULT;
    private Class<?> beanClass;
    private String beanClassName;
    private PropertyValues propertyValues = new MutablePropertyValues();
}

