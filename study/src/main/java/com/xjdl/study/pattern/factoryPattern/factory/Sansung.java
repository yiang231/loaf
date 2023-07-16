package com.xjdl.study.pattern.factoryPattern.factory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Sansung implements Ram {
    @Override
    public void makeRam() {
        log.info("{}", "生产三星内存");
    }
}
