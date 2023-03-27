package com.dl.test.pattern.factoryPattern.factoryMethod;

public class AppleFactory implements FruitFactory{
	@Override
	public Apple makeFruit() {
		return new Apple();
	}
}
