package com.xjdl.study.designPatterns.structuralPatterns.decorator;

import com.xjdl.study.designPatterns.structuralPatterns.decorator.component.Circle;
import com.xjdl.study.designPatterns.structuralPatterns.decorator.component.Rectangle;
import com.xjdl.study.designPatterns.structuralPatterns.decorator.component.Shape;
import com.xjdl.study.designPatterns.structuralPatterns.decorator.decorator.RedBorderDecorator;
import com.xjdl.study.designPatterns.structuralPatterns.decorator.decorator.SizeDecorator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class UseShapeDecorator {
    @Test
    void decorator() {
        log.info("{}", "原始形态");
        Shape circle = new Circle();
        circle.draw();

        log.info("{}", "单层装饰");
        Shape redCircleShapeDecorator = new RedBorderDecorator(circle);
        redCircleShapeDecorator.draw();

        log.info("{}", "多层装饰");
        Shape redRectangleShapeDecorator = new RedBorderDecorator(new Rectangle());
        SizeDecorator sizeRedRectangleShapeDecorator = new SizeDecorator(redRectangleShapeDecorator);
        sizeRedRectangleShapeDecorator.draw();
    }
}
