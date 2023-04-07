package com.dl.test.exception;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 输出到日志的写法
 */
@Slf4j
public class CatchLogError {
	@Test
	public void catchLogError() {
		try {
			throw new RuntimeException();
		} catch (RuntimeException e) {
			log.error("", e);
			log.error(e.getMessage(),e);
		}
	}
}
