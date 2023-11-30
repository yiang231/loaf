package com.xjdl.app.service;

import com.xjdl.framework.beans.factory.DisposableBean;
import com.xjdl.framework.beans.factory.InitializingBean;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Resource
public class OutputService implements InitializingBean, DisposableBean {
	public void output(String text) {
		log.info(text);
	}

	@PostConstruct
	public void initMethod() {
		log.info("OutputService.initMethod");
	}

	public void destroyMethod() {
		log.info("OutputService.destroyMethod");
	}

	@Override
	public void destroy() {
		log.debug("OutputService.destroy");
	}

	@Override
	public void afterPropertiesSet() {
		log.debug("OutputService.afterPropertiesSet");
	}
}
