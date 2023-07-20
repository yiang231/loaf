package com.xjdl.study.springboot.applicationListener;

import com.xjdl.study.springboot.banner.MyBanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.boot.context.logging.LoggingApplicationListener;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.web.context.support.ServletRequestHandledEvent;

import static com.xjdl.study.springboot.banner.MyBanner.MY_BANNER_LOCATION;
import static com.xjdl.study.springboot.banner.MyBanner.MY_BANNER_LOCATION_PROPERTY;
import static com.xjdl.study.springboot.bootstrapRegistryInitializer.MyBootstrapRegistryInitializer.Dlog;

/**
 * 基于事件机制，感知应用全阶段生命周期，某个阶段才能做某些事情
 * <p>
 * 和 {@link LoggingApplicationListener#onApplicationEvent(ApplicationEvent)} 同级别，在环境准备阶段完全加载日志系统
 */
@Slf4j
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {
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
            /*
             * 回放日志
             * */
            if (applicationEvent instanceof ApplicationEnvironmentPreparedEvent) {
                /*
                 * 这里为了输出三个被拒绝的启动阶段日志，使用静态成员 DeferredLog。其他情况下使用延迟日志，声明为成员变量即可
                 * DeferredLog 声明为静态变量，将会在一次回放中将所有延迟日志输出
                 * */
                Dlog.replayTo(getClass());
            }
            log.debug("{}", getQualifierName(applicationEvent));
        }
        if (applicationEvent instanceof ApplicationStartingEvent) {
            // 被拒绝使用日志输出 org.springframework.boot.logging.DeferredLog
            Dlog.debug(getQualifierName(applicationEvent));
        }
        /*
         * 应用环境准备就绪，设置自定义banner
         * */
        if (applicationEvent instanceof ApplicationEnvironmentPreparedEvent) {
            ApplicationEnvironmentPreparedEvent event = (ApplicationEnvironmentPreparedEvent) applicationEvent;
            ConfigurableEnvironment environment = event.getEnvironment();
            String location = environment.getProperty(MY_BANNER_LOCATION_PROPERTY, MY_BANNER_LOCATION);
            Resource resource = new DefaultResourceLoader().getResource(location);
            event.getSpringApplication().setBanner(new MyBanner((resource)));
        }
    }

    private static String getQualifierName(ApplicationEvent applicationEvent) {
        return applicationEvent.getClass().getName();
    }
}

