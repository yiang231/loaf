package com.xjdl.study.pattern.factoryPattern.factory;

public class HuaweiPhone implements PhoneFactory {
    @Override
    public CpuFactory getCpuFactory(Class<? extends Cpu> cpu) throws InstantiationException, IllegalAccessException {
        return new CpuFactory(cpu);
    }

    @Override
    public Ram getRam() {
        return new Sansung();
    }
}
