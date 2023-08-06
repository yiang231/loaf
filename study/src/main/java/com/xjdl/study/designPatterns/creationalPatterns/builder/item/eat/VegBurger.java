package com.xjdl.study.designPatterns.creationalPatterns.builder.item.eat;

public class VegBurger extends Burger {

	@Override
	public float price() {
		return 25.0f;
	}

	@Override
	public String name() {
		return "Veg Burger";
	}
}
