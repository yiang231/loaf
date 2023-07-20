package com.xjdl.study.designPatterns.creationalPatterns.factory.abstractFactory;

public interface PhoneFactory {
    CpuFactory getCpuFactory(Class<? extends Cpu> cpu);

    Ram getRam();
}
