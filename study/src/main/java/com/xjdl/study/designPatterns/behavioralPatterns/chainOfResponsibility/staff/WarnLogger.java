package com.xjdl.study.designPatterns.behavioralPatterns.chainOfResponsibility.staff;

import com.xjdl.study.designPatterns.behavioralPatterns.chainOfResponsibility.AbstractLogger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WarnLogger extends AbstractLogger {
    public WarnLogger(int intLevel) {
        this.intLevel = intLevel;
    }

    @Override
    protected void write(String msg) {
        log.warn("{}", msg);
    }
}
