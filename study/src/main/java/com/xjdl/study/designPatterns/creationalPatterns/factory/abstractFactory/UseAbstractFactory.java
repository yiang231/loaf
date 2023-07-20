package com.xjdl.study.designPatterns.creationalPatterns.factory.abstractFactory;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 抽象工厂 + 代理模式
 */
@Slf4j
public class UseAbstractFactory {
    @Test
    void useAbstractFactory() {
        HuaweiPhone huaweiPhone = new HuaweiPhone();

        huaweiPhone.getCpuFactory(AMD.class).makeCpu();
        huaweiPhone.getCpuFactory(Intel.class).makeCpu();
        huaweiPhone.getRam().makeRam();
    }
}
