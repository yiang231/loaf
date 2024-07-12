package com.xjdl.framework.aop.support;

import java.io.Serializable;

public abstract class AbstractExpressionPointcut implements ExpressionPointcut, Serializable {
	private String location;
	private String expression;

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	protected void onSetExpression(String expression) throws IllegalArgumentException {
	}

	@Override
	public String getExpression() {
		return this.expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
		try {
			onSetExpression(expression);
		} catch (IllegalArgumentException ex) {
			// Fill in location information if possible.
			if (this.location != null) {
				throw new IllegalArgumentException("Invalid expression at location [" + this.location + "]: " + ex);
			} else {
				throw ex;
			}
		}
	}
}
