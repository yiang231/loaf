package com.xjdl.juc.unsafe;

public class Person {
	private String username = "zhangsan";
	private int age = 20;

	@Override
	public String toString() {
		return "Person{" +
				"username='" + username + '\'' +
				", age=" + age +
				'}';
	}
}
