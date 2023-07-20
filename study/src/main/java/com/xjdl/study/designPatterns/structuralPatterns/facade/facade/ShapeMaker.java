package com.xjdl.study.designPatterns.structuralPatterns.facade.facade;

import com.xjdl.study.designPatterns.structuralPatterns.facade.staff.Circle;
import com.xjdl.study.designPatterns.structuralPatterns.facade.staff.Rectangle;
import com.xjdl.study.designPatterns.structuralPatterns.facade.staff.Square;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShapeMaker {
    private Circle circle;
    private Square square;
    private Rectangle rectangle;

    public void drawCircle() {
        circle.draw();
    }

    public void drawRectangle() {
        rectangle.draw();
    }
    public void drawSquare(){
        square.draw();
    }

    public ShapeMaker() {
        this.circle = new Circle();
        this.square = new Square();
        this.rectangle = new Rectangle();
    }
}
