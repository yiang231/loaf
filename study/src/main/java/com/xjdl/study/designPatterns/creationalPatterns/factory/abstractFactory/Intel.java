package com.xjdl.study.designPatterns.creationalPatterns.factory.abstractFactory;

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
