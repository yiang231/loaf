package com.xjdl.study.designPatterns.creationalPatterns.factory.simpleFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * Concrete Product
 */
@Slf4j
public class Apple implements Fruit {
    @Override
    public void myName() {
        log.info("{}", "Apple");
    }
}
