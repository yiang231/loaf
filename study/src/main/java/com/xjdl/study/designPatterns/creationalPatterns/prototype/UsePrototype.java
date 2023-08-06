package com.xjdl.study.designPatterns.creationalPatterns.prototype;

import com.xjdl.study.designPatterns.creationalPatterns.prototype.item.Shape;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 浅拷贝
 */
@Slf4j
public class UsePrototype {
	@Test
	void test() {
		ShapeCache.loadCache();

		Shape c = ShapeCache.getShape("c");
		log.info("Shape : {}", c.getType());

		Shape s = ShapeCache.getShape("s");
		log.info("Shape : {}", s.getType());

		Shape r = ShapeCache.getShape("r");
		log.info("Shape : {}", r.getType());
	}
}
