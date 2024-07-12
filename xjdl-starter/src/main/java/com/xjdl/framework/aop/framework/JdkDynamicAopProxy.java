package com.xjdl.framework.aop.framework;

import com.xjdl.framework.aop.AopInvocationException;
import com.xjdl.framework.util.ClassUtils;
import com.xjdl.framework.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * 基于接口的 JDK 动态代理
 */
@Slf4j
final class JdkDynamicAopProxy implements AopProxy, InvocationHandler, Serializable {
    private final AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport config) throws AopConfigException {
        this.advised = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(ClassUtils.getDefaultClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        if (log.isTraceEnabled()) {
			log.trace("Creating JDK dynamic proxy: {}", this.advised.getTargetSource());
        }
        ClassLoader determineClassLoader = determineClassLoader(classLoader);
        return Proxy.newProxyInstance(determineClassLoader, this.advised.getTargetSource().getTargetClass().getInterfaces(), this);
    }

    /**
     * com.xjdl.framework.aop.framework.JdkDynamicAopProxy#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     * com.xjdl.app.config.DurationMethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     * com.xjdl.framework.aop.framework.ReflectiveMethodInvocation#proceed()
     */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object target = advised.getTargetSource().getTarget();
		Class<?> targetClass = target.getClass();
		Object retVal;
		List<Object> chain = advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
		if (CollectionUtils.isEmpty(chain)) {
			return method.invoke(target, args);
		} else {
			MethodInvocation invocation = new ReflectiveMethodInvocation(proxy, target, method, args, targetClass, chain);
			retVal = invocation.proceed();
		}
		retVal = processReturnType(proxy, target, method, retVal);
		return retVal;
	}

	private static Object processReturnType(Object proxy, Object target, Method method, Object returnValue) {
		Class<?> returnType = method.getReturnType();
		if (returnValue == null && returnType != Void.TYPE && returnType.isPrimitive()) {
			throw new AopInvocationException("Null return value from advice does not match primitive return type for: " + method);
		}
		return returnValue;
	}
    private ClassLoader determineClassLoader(ClassLoader classLoader) {
        if (classLoader == null) {
            // JDK bootstrap loader -> use spring-aop ClassLoader instead.
            return getClass().getClassLoader();
        }
        if (classLoader.getParent() == null) {
            // Potentially the JDK platform loader on JDK 9+
            ClassLoader aopClassLoader = getClass().getClassLoader();
            ClassLoader aopParent = aopClassLoader.getParent();
            while (aopParent != null) {
                if (classLoader == aopParent) {
                    // Suggested ClassLoader is ancestor of spring-aop ClassLoader
                    // -> use spring-aop ClassLoader itself instead.
                    return aopClassLoader;
                }
                aopParent = aopParent.getParent();
            }
        }
        // Regular case: use suggested ClassLoader as-is.
        return classLoader;
    }
}
