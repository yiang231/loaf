package com.xjdl.app.advice;

import com.xjdl.framework.aop.MethodBeforeAdvice;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
public class SimpleMethodBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        log.debug("SimpleMethodBeforeAdvice.before");
    }
}
