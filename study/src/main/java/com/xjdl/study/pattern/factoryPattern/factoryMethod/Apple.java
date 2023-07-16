package com.xjdl.study.pattern.factoryPattern.factoryMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Apple implements Fruit {
	@Override
	public void myName() {
		log.info("{}", "Apple");
	}
}
