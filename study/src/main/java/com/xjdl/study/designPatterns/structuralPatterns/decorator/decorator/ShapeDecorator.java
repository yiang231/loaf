package com.xjdl.study.designPatterns.structuralPatterns.decorator.decorator;

import com.xjdl.study.designPatterns.structuralPatterns.decorator.component.Shape;

/**
 * Decorator
 * 抽象形状装饰类
 */

public abstract class ShapeDecorator implements Shape {
    protected Shape decoratorShape;

    public ShapeDecorator(Shape decoratorShape) {
        this.decoratorShape = decoratorShape;
    }

    @Override
    public void draw() {
        decoratorShape.draw();
    }
}
