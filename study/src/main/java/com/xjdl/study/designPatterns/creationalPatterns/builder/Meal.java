package com.xjdl.study.designPatterns.creationalPatterns.builder;

import com.xjdl.study.designPatterns.creationalPatterns.builder.item.Item;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Meal {
	private List<Item> items = new ArrayList<>();

	public void addItem(Item item) {
		items.add(item);
	}

	public float getCost() {
		float cost = 0.0f;
		for (Item item : items) {
			cost += item.price();
		}
		return cost;
	}

	public void showItems() {
		items.forEach(item -> log.info("Item : {}\tPacking : {}\tPrice : {}", item.name(), item.packing().pack(), item.price()));
	}
}
