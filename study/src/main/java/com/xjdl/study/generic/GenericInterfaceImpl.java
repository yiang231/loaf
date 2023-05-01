package com.xjdl.study.generic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenericInterfaceImpl implements GenericInterface<Object> {
	@Override
	public void show(Object value) {
		log.info("{}", value);
	}
}
