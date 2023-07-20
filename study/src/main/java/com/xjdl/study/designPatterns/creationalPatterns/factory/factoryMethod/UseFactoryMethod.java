package com.xjdl.study.designPatterns.creationalPatterns.factory.factoryMethod;

import org.junit.jupiter.api.Test;

public class UseFactoryMethod {
    @Test
    void useFactoryMethod() {
        AppleFactory appleFactory = new AppleFactory();

        appleFactory.makeFruit().myName();
    }
}
