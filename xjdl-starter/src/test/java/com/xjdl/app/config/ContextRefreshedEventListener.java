package com.xjdl.app.config;

import com.xjdl.framework.context.ApplicationListener;
import com.xjdl.framework.context.event.ContextRefreshedEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info("{}", "ContextRefreshedEventListener.onApplicationEvent");
	}
}
