package com.xjdl.study.exception.globalException;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义异常类
 */
@Slf4j
public class TrueException extends RuntimeException {
	protected String code;
	protected String msg;

	public TrueException() {
		super();
	}

	public TrueException(BaseErrorInfoInterface errorInfoInterface) {
		super(errorInfoInterface.getResultCode());
		this.code = errorInfoInterface.getResultCode();
		this.msg = errorInfoInterface.getResultMsg();
	}

	public TrueException(BaseErrorInfoInterface errorInfoInterface, Throwable cause) {
		super(errorInfoInterface.getResultCode(), cause);
		this.code = errorInfoInterface.getResultCode();
		this.msg = errorInfoInterface.getResultMsg();
	}

	public TrueException(Throwable cause, String code, String msg) {
		super(code, cause);
		this.code = code;
		this.msg = msg;
	}

	public TrueException(String code, String msg) {
		super(code);
		this.code = code;
		this.msg = msg;
	}

	public TrueException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
