package com.xjdl.study.designPatterns.creationalPatterns.builder.item.drink;

import com.xjdl.study.designPatterns.creationalPatterns.builder.item.Item;
import com.xjdl.study.designPatterns.creationalPatterns.builder.item.packing.Bottle;
import com.xjdl.study.designPatterns.creationalPatterns.builder.item.packing.Packing;

public abstract class ColdDrink implements Item {

	@Override
	public Packing packing() {
		return new Bottle();
	}

	@Override
	public abstract float price();
}
