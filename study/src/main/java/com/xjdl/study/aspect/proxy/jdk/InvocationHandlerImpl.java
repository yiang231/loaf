package com.xjdl.study.aspect.proxy.jdk;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class InvocationHandlerImpl implements InvocationHandler {
	private Subject subject;

	public InvocationHandlerImpl(Subject subject) {
		this.subject = subject;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		log.info("前置方法执行");
		log.info("代理类的实例化对象 {}", proxy.getClass());
		Object result = method.invoke(subject, args);
		log.info("后置方法执行");
		return result;
	}
}
