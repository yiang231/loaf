package com.xjdl.study.pattern.factoryPattern.simpleFactory;

public class SimpleFactory {
	public Fruit makeFruit(String name){
		if (name.equals("Apple")) {
			return new Apple();
		}
		return null;
	}
}
