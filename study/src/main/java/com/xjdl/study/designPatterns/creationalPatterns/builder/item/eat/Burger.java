package com.xjdl.study.designPatterns.creationalPatterns.builder.item.eat;

import com.xjdl.study.designPatterns.creationalPatterns.builder.item.Item;
import com.xjdl.study.designPatterns.creationalPatterns.builder.item.packing.Packing;
import com.xjdl.study.designPatterns.creationalPatterns.builder.item.packing.Wrapper;

public abstract class Burger implements Item {

	@Override
	public Packing packing() {
		return new Wrapper();
	}

	@Override
	public abstract float price();
}
