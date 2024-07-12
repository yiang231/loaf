package com.xjdl.framework.aop.framework;

import com.xjdl.framework.aop.Advisor;
import com.xjdl.framework.aop.MethodMatcher;
import com.xjdl.framework.aop.PointcutAdvisor;
import com.xjdl.framework.aop.framework.adapter.AdvisorAdapterRegistry;
import com.xjdl.framework.aop.framework.adapter.GlobalAdvisorAdapterRegistry;
import org.aopalliance.intercept.Interceptor;
import org.aopalliance.intercept.MethodInterceptor;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultAdvisorChainFactory implements AdvisorChainFactory, Serializable {
	@Override
	public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Advised config, Method method, Class<?> targetClass) {
		AdvisorAdapterRegistry registry = GlobalAdvisorAdapterRegistry.getInstance();
		Advisor[] advisors = config.getAdvisors();
		List<Object> interceptorList = new ArrayList<>(advisors.length);
		Class<?> actualClass = (targetClass != null ? targetClass : method.getDeclaringClass());
		for (Advisor advisor : advisors) {
			if (advisor instanceof PointcutAdvisor) {
				PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
				if (pointcutAdvisor.getPointcut().getClassFilter().matches(actualClass)) {
					MethodMatcher mm = pointcutAdvisor.getPointcut().getMethodMatcher();
					boolean match = mm.matches(method, actualClass);
					if (match) {
						MethodInterceptor[] interceptors = registry.getInterceptors(advisor);
						interceptorList.addAll(Arrays.asList(interceptors));
					}
				}
			} else {
				Interceptor[] interceptors = registry.getInterceptors(advisor);
				interceptorList.addAll(Arrays.asList(interceptors));
			}
		}
		return interceptorList;
	}
}
