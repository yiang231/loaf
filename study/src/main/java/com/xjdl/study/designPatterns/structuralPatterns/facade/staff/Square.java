package com.xjdl.study.designPatterns.structuralPatterns.facade.staff;

import com.xjdl.study.designPatterns.structuralPatterns.facade.Shape;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Square implements Shape {
    @Override
    public void draw() {
        log.info("{}", "Draw a square...");
    }
}
