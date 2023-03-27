package com.dl.test.pattern.factoryPattern.factory;

public class UseAbstractFactory {
	public static void main(String[] args) {
		HuaweiPhone huaweiPhone = new HuaweiPhone();
		Cpu cpu = huaweiPhone.getCpu();
		cpu.makeCpu();
	}
}
