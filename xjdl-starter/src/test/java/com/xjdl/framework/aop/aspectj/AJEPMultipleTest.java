package com.xjdl.framework.aop.aspectj;

import com.xjdl.app.service.OutputService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

class AJEPMultipleTest {
    private volatile AspectJExpressionPointcut pointcut;

    static Stream<Arguments> pointcutExpressionProvider() {
        String[] pointcutExpression = {
                "execution(* com.xjdl.app.service.OutputService.destroy*(..))",
                "within(com.xjdl.app.service..*)",
                "target(com.xjdl.app.service.OutputService)",
                "@within(javax.annotation.Resource)"
        };

        return Arrays.stream(pointcutExpression).map(Arguments::of);
    }

    @ParameterizedTest
    @DisplayName("多切点表达式批量多结果测试")
    @MethodSource("com.xjdl.framework.aop.aspectj.AJEPMultipleTest#pointcutExpressionProvider")
    void testWithMultiArgMethodSource(String pointcutExpression) {
        pointcut = new AspectJExpressionPointcut(pointcutExpression);
    }

    @AfterEach
    void afterEach() throws NoSuchMethodException {
        Class<OutputService> outputServiceClass = OutputService.class;
        Method destroyMethod = outputServiceClass.getDeclaredMethod("destroyMethod");
        Method destroy = outputServiceClass.getDeclaredMethod("destroy");

        Assertions.assertTrue(pointcut.matches(outputServiceClass));
        Assertions.assertTrue(pointcut.matches(destroyMethod, outputServiceClass));
        Assertions.assertTrue(pointcut.matches(destroy, outputServiceClass));
    }
}
