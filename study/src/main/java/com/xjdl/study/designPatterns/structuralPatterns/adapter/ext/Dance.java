package com.xjdl.study.designPatterns.structuralPatterns.adapter.ext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Dance implements Excited {
    @Override
    public Dance dance(String name) {
        log.info("Dancing with {}", name);
        return this;
    }
}
