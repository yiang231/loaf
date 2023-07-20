package com.xjdl.study.designPatterns.creationalPatterns.factory.factoryMethod;

public class AppleFactory implements FruitFactory {
    @Override
    public Apple makeFruit() {
        return new Apple();
    }
}
