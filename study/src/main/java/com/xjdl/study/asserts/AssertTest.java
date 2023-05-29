package com.xjdl.study.asserts;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * assert
 * 添加JVM运行参数 -enableassertions或者-ea
 * java.lang.AssertionError 无法捕获
 */
@Slf4j
public class AssertTest {
	@Test
	void assertKeyWord() {
		try {
//			assert false;// 用法 assert boolean condition : message;
			assert false : "返回结果 false";
		} catch (Exception e) {
			log.error("java.lang.AssertionError 无法捕获", e);
		}
	}
}
