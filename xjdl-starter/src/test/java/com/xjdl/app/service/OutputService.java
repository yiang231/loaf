package com.xjdl.app.service;

import com.xjdl.framework.beans.factory.DisposableBean;
import com.xjdl.framework.beans.factory.InitializingBean;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Resource
@Setter
@Getter
public class OutputService implements InitializingBean, DisposableBean, IService {
	private String name;
	public void output(String text) {
		log.info("OutputService.output {}", text);
	}

	@PostConstruct
	public void initMethod() {
		log.debug("OutputService.initMethod");
	}

	public void destroyMethod() {
		log.debug("OutputService.destroyMethod");
	}

	@Override
	public void destroy() {
		log.debug("OutputService.destroy");
	}

	@Override
	public void afterPropertiesSet() {
		log.debug("OutputService.afterPropertiesSet");
	}

	@Override
	public void say() {
		log.info("OutputService.say");
	}
}
