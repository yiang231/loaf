package com.xjdl.study.designPatterns.creationalPatterns.factory.simpleFactory;

import org.junit.jupiter.api.Test;

public class UseSimpleFactory {
    @Test
    void useSimpleFactory() {
        SimpleFactory simpleFactory = new SimpleFactory();

        simpleFactory.makeFruit("Apple").myName();
    }
}
