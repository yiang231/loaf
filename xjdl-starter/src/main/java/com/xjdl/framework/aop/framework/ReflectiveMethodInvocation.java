package com.xjdl.framework.aop.framework;

import lombok.AllArgsConstructor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

@AllArgsConstructor
public class ReflectiveMethodInvocation implements MethodInvocation {
    protected final Object target;

    protected final Method method;
    protected Object[] arguments;

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public Object proceed() throws Throwable {
        return this.method.invoke(target, arguments);
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return this.method;
    }
}
