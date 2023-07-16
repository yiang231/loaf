package com.xjdl.study.springboot.applicationListener;

import com.xjdl.study.springboot.banner.MyBanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.boot.logging.DeferredLog;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.web.context.support.ServletRequestHandledEvent;

import static com.xjdl.study.springboot.banner.MyBanner.MY_BANNER_LOCATION;
import static com.xjdl.study.springboot.banner.MyBanner.MY_BANNER_LOCATION_PROPERTY;

/**
 * 基于事件机制，感知应用全阶段生命周期，某个阶段才能做某些事情
 */
@Slf4j
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {
	private final DeferredLog dlog = new DeferredLog();
	@Override
	public void onApplicationEvent(@NonNull ApplicationEvent applicationEvent) {
		if (log.isDebugEnabled()) {
			if (applicationEvent instanceof ServletRequestHandledEvent) {
				log.debug("{}", applicationEvent);
				return;
			}
			/*
			 * 探针机制
			 * @see org.springframework.boot.availability.LivenessState
			 * @see org.springframework.boot.availability.ReadinessState
			 */
			if (applicationEvent instanceof AvailabilityChangeEvent) {
				AvailabilityChangeEvent event = (AvailabilityChangeEvent) applicationEvent;
				log.debug("{} {}", getQualifierName(event), event.getState());
				return;
			}
			log.debug("{}", getQualifierName(applicationEvent));
		}
		if (applicationEvent instanceof ApplicationStartingEvent) {
			// 被拒绝使用日志输出 org.springframework.boot.logging.DeferredLog
			dlog.info(getQualifierName(applicationEvent));
			return;
		}
		/*
		 * 应用环境准备就绪，设置自定义banner
		 * 回放日志
		 * */
		if (applicationEvent instanceof ApplicationEnvironmentPreparedEvent) {
			ApplicationEnvironmentPreparedEvent event = (ApplicationEnvironmentPreparedEvent) applicationEvent;
			ConfigurableEnvironment environment = event.getEnvironment();
			String location = environment.getProperty(MY_BANNER_LOCATION_PROPERTY, MY_BANNER_LOCATION);
			Resource resource = new DefaultResourceLoader().getResource(location);
			event.getSpringApplication().setBanner(new MyBanner((resource)));
			dlog.replayTo(getClass());
		}
	}

	private static String getQualifierName(ApplicationEvent applicationEvent) {
		return applicationEvent.getClass().getName();
	}
}

