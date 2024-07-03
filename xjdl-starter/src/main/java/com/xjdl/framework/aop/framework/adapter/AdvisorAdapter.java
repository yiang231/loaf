package com.xjdl.framework.aop.framework.adapter;

import com.xjdl.framework.aop.Advisor;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

public interface AdvisorAdapter {
	boolean supportsAdvice(Advice advice);

	MethodInterceptor getInterceptor(Advisor advisor);
}
