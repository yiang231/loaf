package com.xjdl.study.designPatterns.behavioralPatterns.state;

import lombok.Data;

@Data
public class Context {
	State state;

	public Context() {
		this.state = null;
	}
}
