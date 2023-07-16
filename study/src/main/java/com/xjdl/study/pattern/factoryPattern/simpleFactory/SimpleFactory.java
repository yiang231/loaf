package com.xjdl.study.pattern.factoryPattern.simpleFactory;

public class SimpleFactory {
    public Fruit makeFruit(String name) {
        Apple ret = null;
        if (name.equals("Apple")) {
            ret = new Apple();
        }
        return ret;
    }
}
