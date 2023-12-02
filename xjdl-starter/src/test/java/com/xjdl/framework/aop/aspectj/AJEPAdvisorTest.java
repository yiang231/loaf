package com.xjdl.framework.aop.aspectj;

import com.xjdl.app.advice.SimpleAfterReturningAdvice;
import com.xjdl.app.advice.SimpleMethodBeforeAdvice;
import com.xjdl.app.config.DurationMethodInterceptor;
import com.xjdl.app.service.IService;
import com.xjdl.app.service.OutputService;
import com.xjdl.framework.aop.framework.AdvisedSupport;
import com.xjdl.framework.aop.framework.ProxyFactory;
import com.xjdl.framework.aop.framework.adapter.AfterReturningAdviceInterceptor;
import com.xjdl.framework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import com.xjdl.framework.aop.target.TargetSource;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AJEPAdvisorTest {
	private volatile AdvisedSupport advisedSupport;
	private volatile AspectJExpressionPointcutAdvisor aspectJExpressionPointcutAdvisor;

	@BeforeAll
	void beforeAll() {
		String pointcutExpression = "execution(* com.xjdl.app.service.IService.say())";

		aspectJExpressionPointcutAdvisor = new AspectJExpressionPointcutAdvisor();
		aspectJExpressionPointcutAdvisor.setExpression(pointcutExpression);

		IService outputService = new OutputService();
		TargetSource targetSource = new TargetSource(outputService);

		advisedSupport = new AdvisedSupport();
		advisedSupport.setTargetSource(targetSource);
		advisedSupport.setMethodMatcher(aspectJExpressionPointcutAdvisor.getPointcut().getMethodMatcher());
	}

	Stream<MethodInterceptor> adviceInterceptorProvider() {
		return Stream.of(
				new DurationMethodInterceptor(),
				new MethodBeforeAdviceInterceptor(new SimpleMethodBeforeAdvice()),
				new AfterReturningAdviceInterceptor(new SimpleAfterReturningAdvice())
		);
	}

	@ParameterizedTest
	@MethodSource("com.xjdl.framework.aop.aspectj.AJEPAdvisorTest#adviceInterceptorProvider")
	void testAJEPAdvisor(MethodInterceptor adviceInterceptor) {
		aspectJExpressionPointcutAdvisor.setAdvice(adviceInterceptor);
		advisedSupport.setMethodInterceptor((MethodInterceptor) aspectJExpressionPointcutAdvisor.getAdvice());
	}

	@AfterEach
	void afterEach() {
		ProxyFactory proxyFactory = new ProxyFactory(advisedSupport);
		IService outputServiceProxy = (IService) proxyFactory.getProxy();
		outputServiceProxy.say();
	}
}
