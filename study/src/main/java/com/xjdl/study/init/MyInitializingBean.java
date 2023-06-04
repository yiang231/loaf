package com.xjdl.study.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 类上加注解@Component，实现InitializingBean接口
 */
@Component
@Slf4j
public class MyInitializingBean implements InitializingBean {
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("{}", "com.xjdl.study.init.MyInitializingBean.afterPropertiesSet");
	}
}
