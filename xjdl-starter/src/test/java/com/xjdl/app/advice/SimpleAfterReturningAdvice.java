package com.xjdl.app.advice;

import com.xjdl.framework.aop.AfterReturningAdvice;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
public class SimpleAfterReturningAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        log.debug("SimpleAfterReturningAdvice.afterReturning");
    }
}
