package com.xjdl.study.springboot.applicationListener;

import com.xjdl.study.springboot.banner.MyBanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import static com.xjdl.study.springboot.banner.MyBanner.MY_BANNER_LOCATION;
import static com.xjdl.study.springboot.banner.MyBanner.MY_BANNER_LOCATION_PROPERTY;

@Slf4j
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {
	@Override
	public void onApplicationEvent(ApplicationEvent applicationEvent) {
		if (applicationEvent instanceof ApplicationEnvironmentPreparedEvent) {
			ApplicationEnvironmentPreparedEvent event = (ApplicationEnvironmentPreparedEvent) applicationEvent;
			ConfigurableEnvironment environment = event.getEnvironment();
			String location = environment.getProperty(MY_BANNER_LOCATION_PROPERTY, MY_BANNER_LOCATION);
			Resource resource = new DefaultResourceLoader().getResource(location);
			event.getSpringApplication().setBanner(new MyBanner((resource)));
		}
	}
}
