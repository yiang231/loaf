package com.xjdl.framework.aop.support;

import com.xjdl.framework.aop.AopInvocationException;
import com.xjdl.framework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AopUtils {
	public static Object invokeJoinpointUsingReflection(Object target, Method method, Object[] args) throws Throwable {
		try {
			ReflectionUtils.makeAccessible(method);
			return method.invoke(target, args);
		} catch (InvocationTargetException ex) {
			throw ex.getTargetException();
		} catch (IllegalArgumentException ex) {
			throw new AopInvocationException("AOP configuration seems to be invalid: tried calling method [" + method + "] on target [" + target + "]", ex);
		} catch (IllegalAccessException ex) {
			throw new AopInvocationException("Could not access method [" + method + "]", ex);
		}
	}
}
