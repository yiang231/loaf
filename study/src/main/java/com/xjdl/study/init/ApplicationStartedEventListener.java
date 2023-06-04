package com.xjdl.study.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 实现 ApplicationListener<ApplicationStartedEvent>
 */
@Slf4j
@Component
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {
	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		log.info("{}", "com.xjdl.study.init.ApplicationStartedEventListener.onApplicationEvent");
	}
}
