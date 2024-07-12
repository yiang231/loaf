package com.xjdl.framework.aop.framework.adapter;

import com.xjdl.framework.aop.Advisor;
import org.aopalliance.intercept.MethodInterceptor;

public interface AdvisorAdapterRegistry {
	Advisor wrap(Object advice) throws UnknownAdviceTypeException;

	MethodInterceptor[] getInterceptors(Advisor advisor) throws UnknownAdviceTypeException;

	void registerAdvisorAdapter(AdvisorAdapter adapter);
}
