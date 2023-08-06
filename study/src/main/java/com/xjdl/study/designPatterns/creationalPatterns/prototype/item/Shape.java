package com.xjdl.study.designPatterns.creationalPatterns.prototype.item;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public abstract class Shape implements Cloneable {
	public String type;
	public String id;

	abstract void draw();

	public Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			log.error(e.getMessage(), e);
		}
		return clone;
	}
}
