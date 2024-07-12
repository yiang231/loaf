package com.xjdl.framework.aop.framework.adapter;

import com.xjdl.framework.aop.Advisor;
import com.xjdl.framework.aop.AfterReturningAdvice;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.io.Serializable;

class AfterReturningAdviceAdapter implements AdvisorAdapter, Serializable {
	@Override
	public boolean supportsAdvice(Advice advice) {
		return (advice instanceof AfterReturningAdvice);
	}

	@Override
	public MethodInterceptor getInterceptor(Advisor advisor) {
		AfterReturningAdvice advice = (AfterReturningAdvice) advisor.getAdvice();
		return new AfterReturningAdviceInterceptor(advice);
	}
}
