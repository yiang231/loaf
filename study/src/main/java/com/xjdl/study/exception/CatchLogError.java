package com.xjdl.study.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * 输出到日志的写法
 */
@Slf4j
public class CatchLogError {
	public void catchLogError() {
		try {
			throw new RuntimeException();
		} catch (RuntimeException e) {
			log.error("", e);
			log.error(e.getMessage(),e);
		}
	}
}
