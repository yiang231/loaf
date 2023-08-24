package com.xjdl.study.springboot.importBean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * 通过 FactoryBean 注册 Bean
 * <p>
 * 这种方式注册没有进行依赖注入，且是懒加载的
 */
@Component
public class MyFactoryBean implements FactoryBean {
	@Override
	public Object getObject() throws Exception {
		return null;
	}

	@Override
	public Class<?> getObjectType() {
		return null;
	}
}
