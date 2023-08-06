package com.xjdl.study.designPatterns.creationalPatterns.builder;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class UseBuilder {
	@Test
	void test() {
		MealBuilder mealBuilder = new MealBuilder();

		Meal vegMeal = mealBuilder.prepareVegMeal();
		vegMeal.showItems();
		log.info("Total Cost: {}", vegMeal.getCost());

		Meal nonVegMeal = mealBuilder.prepareNonVegMeal();
		nonVegMeal.showItems();
		log.info("Total Cost: {}", nonVegMeal.getCost());
	}
}
