package com.xjdl.framework.exception;

/**
 * 架构异常
 */
public class FrameException extends RuntimeException {
	public FrameException() {
		super();
	}

	public FrameException(String message) {
		super(message);
	}

	public FrameException(Throwable throwable) {
		super(throwable);
	}

	public FrameException(String message, Throwable cause) {
		super(message, cause);
	}
}
