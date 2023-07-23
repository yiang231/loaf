package com.xjdl.test.service;


import com.xjdl.beans.factory.BeanNameAware;
import com.xjdl.beans.factory.InitializingBean;
import com.xjdl.stereotype.Component;

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
