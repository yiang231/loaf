package com.xjdl.study.designPatterns.creationalPatterns.factory.abstractFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CpuFactory implements Cpu {
    Class<? extends Cpu> cpu;

    public CpuFactory(Class<? extends Cpu> cpu) {
        this.cpu = cpu;
    }

    @Override
    public Cpu makeCpu() {
        Cpu ret = null;
        try {
            ret = cpu.newInstance().makeCpu();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
        return ret;
    }
}
