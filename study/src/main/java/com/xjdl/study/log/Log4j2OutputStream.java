package com.xjdl.study.log;

import org.slf4j.Logger;

import java.io.OutputStream;

/**
 * log4j2 输出流
 */
public class Log4j2OutputStream extends OutputStream {
	private final Logger logger;
	private StringBuilder messageBuffer;

	public Log4j2OutputStream(Logger logger) {
        this.logger = logger;
		this.messageBuffer = new StringBuilder();
	}

	@Override
	public void write(int b) {
		char c = (char) b;
		if (c == '\n') {
			logger.info(messageBuffer.toString());
			messageBuffer.setLength(0);
		} else {
			messageBuffer.append(c);
		}
	}
}
