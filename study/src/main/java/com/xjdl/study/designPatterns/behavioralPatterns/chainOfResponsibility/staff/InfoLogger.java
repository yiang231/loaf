package com.xjdl.study.designPatterns.behavioralPatterns.chainOfResponsibility.staff;

import com.xjdl.study.designPatterns.behavioralPatterns.chainOfResponsibility.AbstractLogger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InfoLogger extends AbstractLogger {
    public InfoLogger(int intLevel) {
        this.intLevel = intLevel;
    }

    @Override
    protected void write(String msg) {
        log.info("{}", msg);
    }
}
