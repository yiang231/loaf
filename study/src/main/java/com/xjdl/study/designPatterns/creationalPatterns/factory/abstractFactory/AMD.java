package com.xjdl.study.designPatterns.creationalPatterns.factory.abstractFactory;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class AMD implements Cpu {
    @Override
    public Cpu makeCpu() {
        log.info("{}", "生产AMD处理器");
        return null;
    }
}
