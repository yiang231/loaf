package com.xjdl.app.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OutputService {

	private HelloWorldService helloWorldService;

	public void output(String text) {
		log.info(text);
	}

	public void setHelloWorldService(HelloWorldService helloWorldService) {
		this.helloWorldService = helloWorldService;
	}
}
