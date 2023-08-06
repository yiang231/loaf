package com.xjdl.study.designPatterns.creationalPatterns.builder.item;

import com.xjdl.study.designPatterns.creationalPatterns.builder.item.packing.Packing;

public interface Item {
	String name();

	Packing packing();

	float price();
}
