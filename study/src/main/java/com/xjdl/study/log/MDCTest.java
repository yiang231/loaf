package com.xjdl.study.log;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * MDC简单使用
 */
@Slf4j
public class MDCTest {
    public static final String REQ_ID = "REQ_ID";

    @Test
    void mdcDemo() {
        new Thread(() -> {
            MDC.put(REQ_ID, UUID.randomUUID().toString().replace("-", "").toLowerCase());
            log.info("开始处理业务1");
            log.info("开始处理业务2");
            log.info("开始处理业务3");
            MDC.remove(REQ_ID);
            log.info("{}", MDC.get(REQ_ID));
        }, "线程一").run();

        new Thread(() -> {
            MDC.put(REQ_ID, UUID.randomUUID().toString().replace("-", "").toLowerCase());
            log.info("开始处理业务4");
            log.info("开始处理业务5");
            log.info("开始处理业务6");
            MDC.remove(REQ_ID);
            log.info("{}", MDC.get(REQ_ID));
        }, "线程二").run();
    }
}
