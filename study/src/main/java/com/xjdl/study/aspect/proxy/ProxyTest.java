package com.xjdl.study.aspect.proxy;

import com.xjdl.study.aspect.proxy.cglib.MethodInterceptorImpl;
import com.xjdl.study.aspect.proxy.jdk.InvocationHandlerImpl;
import com.xjdl.study.aspect.proxy.jdk.Subject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

@Slf4j
public class ProxyTest {
	/**
	 * jdk动态代理
	 */
	@Test
	void jdk() {
		RealSubject realSubject = new RealSubject();
		realSubject.req("原始对象测试数据");
		log.info("{}", realSubject.resp());

		InvocationHandlerImpl handler = new InvocationHandlerImpl(realSubject);

		ClassLoader classLoader = realSubject.getClass().getClassLoader();
		Class<?>[] interfaces = realSubject.getClass().getInterfaces();

		/*
		 * 动态代理的核心方法
		 */
		Subject subject = (Subject) Proxy.newProxyInstance(classLoader, interfaces, handler);

		subject.req("jdkProxy测试参数");
		log.info("{}", subject.resp());
	}

	/**
	 * cglib动态代理
	 */
	@Test
	void cglib() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(RealSubject.class);
		// 可以使用lambda表达式
		enhancer.setCallback(new MethodInterceptorImpl(new RealSubject()));
		RealSubject realSubject = (RealSubject) enhancer.create();
		realSubject.req("cglibProxy测试参数");
		log.info("{}", realSubject.resp());
	}
}
