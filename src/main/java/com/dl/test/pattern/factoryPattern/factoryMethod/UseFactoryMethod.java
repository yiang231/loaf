package com.dl.test.pattern.factoryPattern.factoryMethod;

public class UseFactoryMethod {
	public static void main(String[] args) {
		AppleFactory appleFactory = new AppleFactory();
		Apple apple = appleFactory.makeFruit();
		apple.myName();
	}
}
