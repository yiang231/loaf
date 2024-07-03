package com.xjdl.app.config;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class DurationMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long startTime = System.nanoTime();
        String methodName = invocation.getMethod().getName();
		log.debug("Invocation of Method {} start!", methodName);
        Object proceed = invocation.proceed();
        // 1 s【秒】 = 1000 ms【毫秒】 = 1000^2 us【微秒】 = 1000^3 ns【纳秒】 = 1000^4 ps【皮秒】
		log.debug("Invocation of Method {} end! takes {} ns.", methodName, System.nanoTime() - startTime);
        return proceed;
    }
}
