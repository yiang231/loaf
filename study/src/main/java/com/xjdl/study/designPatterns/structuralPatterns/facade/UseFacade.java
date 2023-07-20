package com.xjdl.study.designPatterns.structuralPatterns.facade;

import com.xjdl.study.designPatterns.structuralPatterns.facade.facade.ShapeMaker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class UseFacade {
    @Test
    void test() {
        ShapeMaker shapeMaker = new ShapeMaker();

        shapeMaker.drawCircle();
        shapeMaker.drawRectangle();
        shapeMaker.drawSquare();
    }
}
