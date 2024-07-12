package com.xjdl.framework.aop.framework.adapter;

import com.xjdl.framework.aop.Advisor;
import com.xjdl.framework.aop.ThrowsAdvice;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.io.Serializable;

class ThrowsAdviceAdapter implements AdvisorAdapter, Serializable {
	@Override
	public boolean supportsAdvice(Advice advice) {
		return (advice instanceof ThrowsAdvice);
	}

	@Override
	public MethodInterceptor getInterceptor(Advisor advisor) {
		return new ThrowsAdviceInterceptor(advisor.getAdvice());
	}
}
