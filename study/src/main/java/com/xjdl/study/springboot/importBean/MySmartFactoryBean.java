package com.xjdl.study.springboot.importBean;

import org.springframework.beans.factory.SmartFactoryBean;
import org.springframework.stereotype.Component;

/**
 * 通过 SmartFactoryBean 注册 Bean
 */
@Component
public class MySmartFactoryBean implements SmartFactoryBean {
	@Override
	public Object getObject() throws Exception {
		return null;
	}

	@Override
	public Class<?> getObjectType() {
		return null;
	}

	/**
	 * 指明非懒加载
	 */
	@Override
	public boolean isEagerInit() {
		return true;
	}
}
