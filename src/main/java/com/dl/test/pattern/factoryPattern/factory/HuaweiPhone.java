package com.dl.test.pattern.factoryPattern.factory;

public class HuaweiPhone implements PhoneFactory{
	@Override
	public Cpu getCpu() {
		return new Cpu888();
	}
}
