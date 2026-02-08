package com.xjdl.study.designPatterns.behavioralPatterns.delegate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HpPrinter implements Printer {
    @Override
    public void print(String msg) {
        log.info("HpPrinter {}", msg);
    }
}
