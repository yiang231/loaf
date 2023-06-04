package com.xjdl.study.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 实现 ApplicationListener<ApplicationReadyEvent>
 */
@Slf4j
@Component
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		log.info("{}", "com.xjdl.study.init.ApplicationReadyEventListener.onApplicationEvent");
	}
}
