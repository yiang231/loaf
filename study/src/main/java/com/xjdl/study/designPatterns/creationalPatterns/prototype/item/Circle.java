package com.xjdl.study.designPatterns.creationalPatterns.prototype.item;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Circle extends Shape {

	public Circle() {
		type = "Circle";
	}

	@Override
	public void draw() {
		log.info("{}", "Circle.draw");
	}
}
