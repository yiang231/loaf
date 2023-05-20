package com.xjdl.study.scheduleTask;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 使用SpringTask实现定时任务
 */
@Slf4j
@Component
public class SpringScheduled {
    //    @Scheduled(cron = "0/5 * * * * ?")
    @Scheduled(fixedRate = 1L, timeUnit = TimeUnit.DAYS)
    public void log() {
        log.info("SpringScheduled... @EnableScheduling");
    }
}
