package com.xjdl.study.designPatterns.structuralPatterns.decorator.decorator;

import com.xjdl.study.designPatterns.structuralPatterns.decorator.component.Shape;
import lombok.extern.slf4j.Slf4j;

/**
 * Concrete Decorator
 * 扩展形状抽象装饰类的大小实体装饰类
 */
@Slf4j
public class SizeDecorator extends ShapeDecorator {
    public SizeDecorator(Shape decoratorShape) {
        super(decoratorShape);
    }

    @Override
    public void draw() {
        decoratorShape.draw();
        setLarge(decoratorShape);
    }

    private void setLarge(Shape decoratorShape) {
        log.info("{}", "Size: larger");
    }
}
