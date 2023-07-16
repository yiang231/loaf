package com.xjdl.study.pattern.factoryPattern.factory;

public class CpuFactory {
    Class<? extends Cpu> cpu;

    public CpuFactory(Class<? extends Cpu> cpu) {
        this.cpu = cpu;
    }

    Cpu makeCpu() throws InstantiationException, IllegalAccessException {
        return cpu.newInstance().makeCpu();
    }
}
