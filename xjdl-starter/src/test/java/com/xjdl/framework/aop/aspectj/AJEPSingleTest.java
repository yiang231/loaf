package com.xjdl.framework.aop.aspectj;

import com.xjdl.app.service.OutputService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

class AJEPSingleTest {
    private volatile AspectJExpressionPointcut pointcut;

    @Test
    void testARGS() throws NoSuchMethodException {
        String pointcutExpression = "args(java.lang.String)";
        pointcut = new AspectJExpressionPointcut(pointcutExpression);

        Class<OutputService> outputServiceClass = OutputService.class;
        Method output = outputServiceClass.getDeclaredMethod("output", String.class);

        Assertions.assertTrue(pointcut.matches(outputServiceClass));
        Assertions.assertTrue(pointcut.matches(output, outputServiceClass));
    }

    @Test
    void testAT_ANNOTATION() throws NoSuchMethodException {
        String pointcutExpression = "@annotation(javax.annotation.PostConstruct)";
        pointcut = new AspectJExpressionPointcut(pointcutExpression);

        Class<OutputService> outputServiceClass = OutputService.class;
        Method initMethod = outputServiceClass.getDeclaredMethod("initMethod");

        Assertions.assertTrue(pointcut.matches(outputServiceClass));
        Assertions.assertTrue(pointcut.matches(initMethod, outputServiceClass));
    }
}
