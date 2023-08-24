package com.xjdl.juc.volatileKeyWord;

public class Data {
	//	int num = 0;
	volatile int num = 0;

	public void addNum() {
		this.num = 88;
	}
}
