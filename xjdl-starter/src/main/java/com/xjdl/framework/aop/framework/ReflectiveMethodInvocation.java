package com.xjdl.framework.aop.framework;

import com.xjdl.framework.aop.support.AopUtils;
import lombok.AllArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

@AllArgsConstructor
public class ReflectiveMethodInvocation implements MethodInvocation {
	protected final Object proxy;
	protected final Object target;
	private final Class<?> targetClass;
	protected final Method method;
	protected Object[] arguments;
	protected final List<?> interceptorsAndDynamicMethodMatchers;
	private int currentInterceptorIndex = -1;

	protected ReflectiveMethodInvocation(Object proxy, Object target, Method method, Object[] arguments, Class<?> targetClass, List<Object> interceptorsAndDynamicMethodMatchers) {
		this.proxy = proxy;
		this.target = target;
		this.targetClass = targetClass;
		this.method = method;
		this.arguments = arguments;
		this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
	}

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
		if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
			return invokeJoinpoint();
		}
		Object interceptorOrInterceptionAdvice = this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
		return ((MethodInterceptor) interceptorOrInterceptionAdvice).invoke(this);
	}

	protected Object invokeJoinpoint() throws Throwable {
		return AopUtils.invokeJoinpointUsingReflection(this.target, this.method, this.arguments);
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
