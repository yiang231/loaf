package com.xjdl.app.config;

import com.xjdl.framework.context.ApplicationListener;
import com.xjdl.framework.context.event.ContextClosedEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {
	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		log.info("{}", "ContextClosedEventListener.onApplicationEvent");
	}
}
