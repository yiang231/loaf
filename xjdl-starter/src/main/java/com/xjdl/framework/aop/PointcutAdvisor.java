package com.xjdl.framework.aop;

public interface PointcutAdvisor extends Advisor {
	Pointcut getPointcut();
}
