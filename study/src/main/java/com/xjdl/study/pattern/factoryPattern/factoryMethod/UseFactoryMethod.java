package com.xjdl.study.pattern.factoryPattern.factoryMethod;

import org.junit.jupiter.api.Test;

public class UseFactoryMethod {
    @Test
    void useFactoryMethod() {
        AppleFactory appleFactory = new AppleFactory();

        appleFactory.makeFruit().myName();
    }
}
