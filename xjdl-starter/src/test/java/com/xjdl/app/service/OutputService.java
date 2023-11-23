package com.xjdl.app.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OutputService {
    public void output(String text) {
        log.info(text);
    }

    public void initMethodName() {
        log.info("initMethod");
    }

    public void destroyMethodName() {
        log.info("destroyMethod");
    }
}
