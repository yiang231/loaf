package com.xjdl.framework.aop.aspectj;

import com.xjdl.framework.aop.ClassFilter;
import com.xjdl.framework.aop.MethodMatcher;
import com.xjdl.framework.aop.Pointcut;
import com.xjdl.framework.util.ClassUtils;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class AspectJExpressionPointcut implements Pointcut, MethodMatcher, ClassFilter {
    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<>();
    private transient PointcutExpression pointcutExpression;

    static {
        /*
         * execution(modifiers returnType declaringType.name(parameterTypes) throwsPattern)
         *
         * modifiers 省略时匹配任意修饰符
         * returnType *匹配任意类型
         * declaringType 省略时匹配任意类型，..匹配包及其子包的所有类
         * name *匹配任意方法，set*匹配set开头的方法
         * parameterTypes ()匹配无参，(..)匹配任意参数，(*)匹配单个任意类型参数，(*, String)匹配第一个任意类型参数，第二个为 String 类型参数。不能匹配参数类型为子类的方法
         * throwsPattern 省略时匹配任意类型
         */
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
        /*
         * args(parameterTypes)
         *
         * 匹配方法参数类型和数量，参数类型可以为指定类型及其子类。
         */
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.ARGS);
//        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.REFERENCE);
        /*
         * this(declaringType)
         *
         * 匹配代理对象实例的类型，匹配在运行时对象的类型。
         * 基于 JDK 动态代理实现的 AOP，this 不能匹配接口的实现类，因为代理类和实现类并不是同一种类型
         */
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.THIS);
        /*
         * target(declaringType)
         *
         * 匹配目标对象实例的类型，匹配 AOP 被代理对象的类型。
         */
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.TARGET);
        /*
         * within(declaringType)
         *
         * 匹配指定类型。匹配指定类的任意方法，不能匹配接口。
         */
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.WITHIN);
        /*
         * @annotation(declaringType)
         *
         * 匹配方法是否含有注解。当方法上使用了注解，该方法会被匹配，在接口方法上使用注解不匹配。
         */
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ANNOTATION);
        /*
         * @within(declaringType)
         *
         * 匹配指定类型是否含有注解。当定义类时使用了注解，该类的方法会被匹配，但在接口上使用注解不匹配。
         */
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_WITHIN);
        /*
         * @args(declaringType)
         *
         * 匹配方法参数类型是否含有注解。当方法的参数类型上使用了注解，该方法会被匹配。
         */
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ARGS);
        /*
         * @target(declaringType)
         *
         * 匹配目标对象实例的类型是否含有注解。当运行时对象实例的类型使用了注解，该类的方法会被匹配，在接口上使用注解不匹配。
         */
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_TARGET);
    }

    public AspectJExpressionPointcut() {
    }

    public AspectJExpressionPointcut(String pointcutExpression) {
        PointcutParser pointcutParser = PointcutParser
                .getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(SUPPORTED_PRIMITIVES, ClassUtils.getDefaultClassLoader());
        this.pointcutExpression = pointcutParser.parsePointcutExpression(pointcutExpression);
    }

    @Override
    public boolean matches(Class<?> clazz) {
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object... args) {
        return pointcutExpression.matchesMethodExecution(method)
                .maybeMatches();
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
