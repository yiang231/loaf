package com.xjdl.app.service;

public class HelloWorldService {
	private String text;
	private OutputService outputService;

	public OutputService getOutputService() {
		outputService.output("outputService");
		return outputService;
	}

	public void setOutputService(OutputService outputService) {
		this.outputService = outputService;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
