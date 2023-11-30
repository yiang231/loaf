package com.xjdl.framework.aop.framework;

import com.xjdl.framework.util.ClassUtils;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
            log.trace("Creating JDK dynamic proxy: " + this.advised.getTargetSource());
        }
        ClassLoader determineClassLoader = determineClassLoader(classLoader);
        return Proxy.newProxyInstance(determineClassLoader, this.advised.getTargetSource().getTargetClass(), this);
    }

    /**
     * com.xjdl.framework.aop.framework.JdkDynamicAopProxy#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     * com.xjdl.app.config.DurationMethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     * com.xjdl.framework.aop.framework.ReflectiveMethodInvocation#proceed()
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean matches = advised.getMethodMatcher().matches(method, advised.getTargetSource().getTarget().getClass());
        if (matches) {
            // 方法被切入
            MethodInterceptor methodInterceptor = advised.getMethodInterceptor();
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(advised.getTargetSource().getTarget(), method, args));
        }
        return method.invoke(advised.getTargetSource().getTarget(), args);
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
