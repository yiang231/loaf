package com.xjdl.framework.aop.framework.adapter;

import com.xjdl.app.advice.SimpleAfterReturningAdvice;
import com.xjdl.app.advice.SimpleMethodBeforeAdvice;
import com.xjdl.app.config.DurationMethodInterceptor;
import com.xjdl.app.service.IService;
import com.xjdl.app.service.OutputService;
import com.xjdl.framework.aop.TargetSource;
import com.xjdl.framework.aop.aspectj.AspectJExpressionPointcut;
import com.xjdl.framework.aop.framework.AdvisedSupport;
import com.xjdl.framework.aop.framework.ProxyFactory;
import com.xjdl.framework.aop.target.SingletonTargetSource;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdviceInterceptorTest {
    private volatile AdvisedSupport advisedSupport;

    @BeforeAll
    void beforeAll() {
        IService outputService = new OutputService();
        TargetSource targetSource = new SingletonTargetSource(outputService);

        String pointcutExpression = "execution(* com.xjdl.app.service.IService.say())";
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut(pointcutExpression);

        advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(targetSource);
        advisedSupport.setMethodMatcher(pointcut);
    }

    Stream<MethodInterceptor> adviceInterceptorProvider() {
        return Stream.of(
                new DurationMethodInterceptor(),
                new MethodBeforeAdviceInterceptor(new SimpleMethodBeforeAdvice()),
                new AfterReturningAdviceInterceptor(new SimpleAfterReturningAdvice())
        );
    }

    @ParameterizedTest
    @MethodSource("com.xjdl.framework.aop.framework.adapter.AdviceInterceptorTest#adviceInterceptorProvider")
    void testAdviceInterceptor(MethodInterceptor adviceInterceptor) {
        advisedSupport.setMethodInterceptor(adviceInterceptor);
    }

    @AfterEach
    void afterEach() {
        ProxyFactory proxyFactory = new ProxyFactory(advisedSupport);
        IService outputServiceProxy = (IService) proxyFactory.getProxy();
        outputServiceProxy.say();
    }
}