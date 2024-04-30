package com.xjdl.framework.aop.framework;

import com.xjdl.app.config.DurationMethodInterceptor;
import com.xjdl.app.service.IService;
import com.xjdl.app.service.OutputService;
import com.xjdl.framework.aop.TargetSource;
import com.xjdl.framework.aop.aspectj.AspectJExpressionPointcut;
import com.xjdl.framework.aop.target.SingletonTargetSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AopProxyTest {
    private volatile AdvisedSupport advisedSupport;

    @BeforeAll
    void beforeAll() {
        IService outputService = new OutputService();
        TargetSource targetSource = new SingletonTargetSource(outputService);

        DurationMethodInterceptor methodInterceptor = new DurationMethodInterceptor();

        // 对于 JdkDynamicAopProxy 这里不能切实现类，实际的方法是 public abstract void com.xjdl.app.service.IService.say()
        // 对于 CglibAopProxy ，实际的方法是 public void com.xjdl.app.service.OutputService.say()
        String pointcutExpression = "execution(* com.xjdl.app.service.IService.say())";
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut(pointcutExpression);

        advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(targetSource);
        advisedSupport.setMethodInterceptor(methodInterceptor);
        advisedSupport.setMethodMatcher(pointcut);
    }

    Stream<AopProxy> aopProxyProvider() {
        return Stream.of(
                new JdkDynamicAopProxy(advisedSupport),
                new CglibAopProxy(advisedSupport)
        );
    }

    @ParameterizedTest
    @MethodSource("com.xjdl.framework.aop.framework.AopProxyTest#aopProxyProvider")
    void testAopProxy(AopProxy aopProxy) {
        IService outputServiceProxy = (IService) aopProxy.getProxy();
        outputServiceProxy.say();
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void testAopProxyFactory(Boolean isProxyTargetClass) {
        advisedSupport.setProxyTargetClass(isProxyTargetClass);

        AopProxyFactory aopProxyFactory = new DefaultAopProxyFactory();
        AopProxy aopProxy = aopProxyFactory.createAopProxy(advisedSupport);

        IService outputServiceProxy = (IService) aopProxy.getProxy();
        outputServiceProxy.say();
    }

    @Test
    void testProxyFactory() {
        ProxyFactory proxyFactory = new ProxyFactory(advisedSupport);
        IService outputServiceProxy = (IService) proxyFactory.getProxy();
        outputServiceProxy.say();
    }
}