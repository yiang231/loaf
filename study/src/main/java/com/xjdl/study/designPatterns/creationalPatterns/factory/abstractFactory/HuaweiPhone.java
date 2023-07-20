package com.xjdl.study.designPatterns.creationalPatterns.factory.abstractFactory;

public class HuaweiPhone implements PhoneFactory {
    @Override
    public CpuFactory getCpuFactory(Class<? extends Cpu> cpu) {
        return new CpuFactory(cpu);
    }

    @Override
    public Ram getRam() {
        return new Sansung();
    }
}
