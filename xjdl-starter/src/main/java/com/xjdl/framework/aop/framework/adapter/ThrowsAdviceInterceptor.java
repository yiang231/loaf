package com.xjdl.framework.aop.framework.adapter;

import com.xjdl.framework.aop.AfterAdvice;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ThrowsAdviceInterceptor implements MethodInterceptor, AfterAdvice {
	private static final String AFTER_THROWING = "afterThrowing";
	private final Object throwsAdvice;
	private final Map<Class<?>, Method> exceptionHandlerMap = new HashMap<>();

	public ThrowsAdviceInterceptor(Object throwsAdvice) {
		assert null != throwsAdvice : "Advice must not be null";
		this.throwsAdvice = throwsAdvice;

		Method[] methods = throwsAdvice.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().equals(AFTER_THROWING) &&
					(method.getParameterCount() == 1 || method.getParameterCount() == 4)) {
				Class<?> throwableParam = method.getParameterTypes()[method.getParameterCount() - 1];
				if (Throwable.class.isAssignableFrom(throwableParam)) {
					this.exceptionHandlerMap.put(throwableParam, method);
					if (log.isDebugEnabled()) {
						log.debug("Found exception handler method on throws advice: {}", method);
					}
				}
			}
		}

		if (this.exceptionHandlerMap.isEmpty()) {
			throw new IllegalArgumentException("At least one handler method must be found in class [" + throwsAdvice.getClass() + "]");
		}
	}

	public int getHandlerMethodCount() {
		return this.exceptionHandlerMap.size();
	}

	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		try {
			return mi.proceed();
		} catch (Throwable ex) {
			Method handlerMethod = getExceptionHandler(ex);
			if (handlerMethod != null) {
				invokeHandlerMethod(mi, ex, handlerMethod);
			}
			throw ex;
		}
	}

	private Method getExceptionHandler(Throwable exception) {
		Class<?> exceptionClass = exception.getClass();
		if (log.isTraceEnabled()) {
			log.trace("Trying to find handler for exception of type [{}]", exceptionClass.getName());
		}
		Method handler = this.exceptionHandlerMap.get(exceptionClass);
		while (handler == null && exceptionClass != Throwable.class) {
			exceptionClass = exceptionClass.getSuperclass();
			handler = this.exceptionHandlerMap.get(exceptionClass);
		}
		if (handler != null && log.isTraceEnabled()) {
			log.trace("Found handler for exception of type [{}]: {}", exceptionClass.getName(), handler);
		}
		return handler;
	}

	private void invokeHandlerMethod(MethodInvocation mi, Throwable ex, Method method) throws Throwable {
		Object[] handlerArgs;
		if (method.getParameterCount() == 1) {
			handlerArgs = new Object[]{ex};
		} else {
			handlerArgs = new Object[]{mi.getMethod(), mi.getArguments(), mi.getThis(), ex};
		}
		try {
			method.invoke(this.throwsAdvice, handlerArgs);
		} catch (InvocationTargetException targetEx) {
			throw targetEx.getTargetException();
		}
	}
}
