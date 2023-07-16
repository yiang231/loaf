package com.xjdl.study.pattern.factoryPattern.factory;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 抽象工厂，加策略模式【解决膨胀问题】
 */
@Slf4j
public class UseAbstractFactory {
    @Test
    void useAbstractFactory() throws InstantiationException, IllegalAccessException {
        HuaweiPhone huaweiPhone = new HuaweiPhone();

        huaweiPhone.getCpuFactory(AMD.class).makeCpu();
        huaweiPhone.getCpuFactory(Intel.class).makeCpu();
        huaweiPhone.getRam().makeRam();
    }
}
