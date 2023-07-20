package com.xjdl.study.designPatterns.structuralPatterns.decorator.component;

import lombok.extern.slf4j.Slf4j;

/**
 * Concrete Component
 */
@Slf4j
public class Circle implements Shape {
    @Override
    public void draw() {
        log.info("{}", "Shape: Circle");
    }
}
