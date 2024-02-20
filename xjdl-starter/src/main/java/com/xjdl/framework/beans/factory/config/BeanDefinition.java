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
    String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;
    String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;
    public static final String SCOPE_DEFAULT = "";
    private String initMethodName;
    private String destroyMethodName;
    private Boolean lazyInit;
    private String scope = SCOPE_SINGLETON;
    private Class<?> beanClass;
    private String beanClassName;
    private PropertyValues propertyValues = new MutablePropertyValues();

    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(this.scope) || SCOPE_DEFAULT.equals(this.scope);
    }

    public boolean isPrototype() {
        return SCOPE_PROTOTYPE.equals(this.scope);
    }

    public boolean hasPropertyValues() {
        return (this.propertyValues != null && !this.propertyValues.isEmpty());
    }
}

