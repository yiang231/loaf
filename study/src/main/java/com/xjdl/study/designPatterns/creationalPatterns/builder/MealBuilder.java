package com.xjdl.study.designPatterns.creationalPatterns.builder;

import com.xjdl.study.designPatterns.creationalPatterns.builder.item.drink.Coke;
import com.xjdl.study.designPatterns.creationalPatterns.builder.item.drink.Pepsi;
import com.xjdl.study.designPatterns.creationalPatterns.builder.item.eat.ChickenBurger;
import com.xjdl.study.designPatterns.creationalPatterns.builder.item.eat.VegBurger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MealBuilder {
	public Meal prepareVegMeal() {
		log.info("{}", "Veg Meal");
		Meal meal = new Meal();
		meal.addItem(new VegBurger());
		meal.addItem(new Coke());
		return meal;
	}

	public Meal prepareNonVegMeal() {
		log.info("{}", "Non-Veg Meal");
		Meal meal = new Meal();
		meal.addItem(new ChickenBurger());
		meal.addItem(new Pepsi());
		return meal;
	}
}
