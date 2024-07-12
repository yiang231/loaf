package com.xjdl.framework.aop.framework.adapter;

import com.xjdl.framework.aop.Advisor;
import com.xjdl.framework.aop.MethodBeforeAdvice;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.io.Serializable;

class MethodBeforeAdviceAdapter implements AdvisorAdapter, Serializable {

	@Override
	public boolean supportsAdvice(Advice advice) {
		return (advice instanceof MethodBeforeAdvice);
	}

	@Override
	public MethodInterceptor getInterceptor(Advisor advisor) {
		MethodBeforeAdvice advice = (MethodBeforeAdvice) advisor.getAdvice();
		return new MethodBeforeAdviceInterceptor(advice);
	}
}
