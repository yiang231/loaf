package com.xjdl.study.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 3.类上加注解@Component，实现InitializingBean接口
 */
@Component
@Slf4j
public class InitializingBean3 implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("InitializingBean#InitTest3.afterPropertiesSet");
	}
}
