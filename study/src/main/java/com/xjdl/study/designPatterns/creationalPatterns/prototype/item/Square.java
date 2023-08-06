package com.xjdl.study.designPatterns.creationalPatterns.prototype.item;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Square extends Shape {

	public Square() {
		type = "Square";
	}

	@Override
	public void draw() {
		log.info("{}", "Square.draw");
	}
}
