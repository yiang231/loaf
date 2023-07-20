package com.xjdl.study.designPatterns.creationalPatterns.factory.simpleFactory;

/**
 * Abstract Factory
 */
public class SimpleFactory {
    public Fruit makeFruit(String name) {
        Apple ret = null;
        if (name.equals("Apple")) {
            ret = new Apple();
        }
        return ret;
    }
}
