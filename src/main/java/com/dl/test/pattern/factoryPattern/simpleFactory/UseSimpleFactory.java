package com.dl.test.pattern.factoryPattern.simpleFactory;

public class UseSimpleFactory {
	public static void main(String[] args) {
		SimpleFactory simpleFactory = new SimpleFactory();
		Fruit fruit = simpleFactory.makeFruit("Apple");
		fruit.myName();
	}
}
