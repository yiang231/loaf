package com.xjdl.study.pattern.factoryPattern.factory;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Intel implements Cpu {
    @Override
    public Cpu makeCpu() {
        log.info("{}", "生产Intel处理器");
        return null;
    }
}
