package com.xjdl.study.chain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class Student {
	private int id;
	private String name;
	private String gender;

	public Student setId(int id) {
		this.id = id;
		return this;
	}

	public Student setName(String name) {
		this.name = name;
		return this;
	}

	public Student setGender(String gender) {
		this.gender = gender;
		return this;
	}
}
