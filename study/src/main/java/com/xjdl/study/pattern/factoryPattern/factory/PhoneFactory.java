package com.xjdl.study.pattern.factoryPattern.factory;

public interface PhoneFactory {
    CpuFactory getCpuFactory(Class<? extends Cpu> cpu) throws InstantiationException, IllegalAccessException;

    Ram getRam();
}
