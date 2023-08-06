package com.xjdl.study.designPatterns.creationalPatterns.prototype.item;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Rectangle extends Shape {

	public Rectangle() {
		type = "Rectangle";
	}

	@Override
	public void draw() {
		log.info("{}", "Rectangle.draw");
	}
}
