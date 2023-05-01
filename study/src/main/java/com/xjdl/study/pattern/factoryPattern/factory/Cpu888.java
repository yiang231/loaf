package com.xjdl.study.pattern.factoryPattern.factory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Cpu888 implements Cpu {
	@Override
	public void makeCpu() {
		log.info("生产高通骁龙888Cpu");
	}
}
