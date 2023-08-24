package com.xjdl.app.service;


import com.xjdl.framework.beans.factory.BeanNameAware;
import com.xjdl.framework.beans.factory.InitializingBean;
import com.xjdl.framework.stereotype.Component;

@Component
public class OrderService implements BeanNameAware, InitializingBean {
    private String beanName;

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("OrderService.afterPropertiesSet");
    }
}
