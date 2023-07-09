package com.xjdl.study.springboot.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 观察者模式
 * @see ApplicationEventPublisherAware 注入了 ApplicationEventPublisher
 */
@Component
public class MyApplicationEventPublisher implements ApplicationEventPublisherAware {
	ApplicationEventPublisher applicationEventPublisher;

	/**
	 * 调用底层API发送事件
	 */
	public void sendEvent(ApplicationEvent event) {
		applicationEventPublisher.publishEvent(event);
	}

	@Override
	public void setApplicationEventPublisher(@NonNull ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
}
