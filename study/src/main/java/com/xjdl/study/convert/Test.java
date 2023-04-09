package com.xjdl.study.convert;

import lombok.extern.slf4j.Slf4j;

/**
 * 面试题
 */
@Slf4j
public class Test {
	public static void main(String[] args) {
		int x = Integer.MAX_VALUE;
		log.info("{}", x);
		log.info("{}", x + 1);
		if (x + 1 < x) {
			log.info("{}", x);
		}
		log.info("{}", (x++) * 3);
	}
}
