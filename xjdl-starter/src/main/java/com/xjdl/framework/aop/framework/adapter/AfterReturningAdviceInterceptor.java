package com.xjdl.framework.aop.framework.adapter;

import com.xjdl.framework.aop.AfterAdvice;
import com.xjdl.framework.aop.AfterReturningAdvice;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.io.Serializable;

@Setter
@NoArgsConstructor
public class AfterReturningAdviceInterceptor implements MethodInterceptor, AfterAdvice, Serializable {
    private AfterReturningAdvice advice;

    public AfterReturningAdviceInterceptor(AfterReturningAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        Object retVal = mi.proceed();
        this.advice.afterReturning(retVal, mi.getMethod(), mi.getArguments(), mi.getThis());
        return retVal;
    }
}