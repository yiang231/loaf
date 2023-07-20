package com.xjdl.study.designPatterns.structuralPatterns.decorator.decorator;

import com.xjdl.study.designPatterns.structuralPatterns.decorator.component.Shape;
import lombok.extern.slf4j.Slf4j;

/**
 * Concrete Decorator
 * 扩展形状抽象装饰类的颜色实体装饰类
 */
@Slf4j
public class RedBorderDecorator extends ShapeDecorator {
    public RedBorderDecorator(Shape decoratorShape) {
        super(decoratorShape);
    }

    @Override
    public void draw() {
        decoratorShape.draw();
        setRedBorder(decoratorShape);
    }

    /**
     * 装饰 border
     */
    private void setRedBorder(Shape decoratorShape) {
        log.info("{}", "Border color: Red");
    }
}
