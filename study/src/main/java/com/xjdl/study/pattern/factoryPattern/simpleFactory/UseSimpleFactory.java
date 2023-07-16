package com.xjdl.study.pattern.factoryPattern.simpleFactory;

import org.junit.jupiter.api.Test;

public class UseSimpleFactory {
    @Test
    void useSimpleFactory() {
        SimpleFactory simpleFactory = new SimpleFactory();

        simpleFactory.makeFruit("Apple").myName();
    }
}
