package com.xjdl.framework.aop.framework;

import com.xjdl.framework.aop.AopInvocationException;
import com.xjdl.framework.aop.support.AopUtils;
import com.xjdl.framework.util.ClassUtils;
import com.xjdl.framework.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.core.CodeGenerationException;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 基于 CGLib 的动态代理
 *
 * @see org.springframework.aop.framework.ObjenesisCglibAopProxy
 */
@Slf4j
class CglibAopProxy implements AopProxy, Serializable {
    private final AdvisedSupport advised;

    public CglibAopProxy(AdvisedSupport config) throws AopConfigException {
        this.advised = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(ClassUtils.getDefaultClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        if (log.isTraceEnabled()) {
			log.trace("Creating CGLIB proxy: {}", this.advised.getTargetSource());
        }
        try {
            Enhancer enhancer = createEnhancer();
            if (classLoader != null) {
                enhancer.setClassLoader(classLoader);
            }
            enhancer.setSuperclass(advised.getTargetSource().getTargetClass());
            enhancer.setInterfaces(advised.getTargetSource().getTargetClass().getInterfaces());
            enhancer.setCallback(new DynamicAdvisedInterceptor(advised));
            return enhancer.create();
        } catch (CodeGenerationException | IllegalArgumentException ex) {
            throw new AopConfigException("Could not generate CGLIB subclass of " + this.advised.getTargetSource() +
                    ": Common causes of this problem include using a final class or a non-visible class", ex);
        }
    }

    protected Enhancer createEnhancer() {
        return new Enhancer();
    }

    private static class DynamicAdvisedInterceptor implements MethodInterceptor {

        private final AdvisedSupport advised;

        private DynamicAdvisedInterceptor(AdvisedSupport advised) {
            this.advised = advised;
        }

        /**
         * com.xjdl.framework.aop.framework.CglibAopProxy.DynamicAdvisedInterceptor#intercept(java.lang.Object, java.lang.reflect.Method, java.lang.Object[], net.sf.cglib.proxy.MethodProxy)
         * com.xjdl.app.config.DurationMethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
         * com.xjdl.framework.aop.framework.ReflectiveMethodInvocation#proceed()
         */
        @Override
        public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            Object target = advised.getTargetSource().getTarget();
            Class<?> targetClass = target.getClass();
            List<Object> chain = advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
            Object retVal;
            if (CollectionUtils.isEmpty(chain)) {
                retVal = invokeMethod(target, method, args, methodProxy);
            } else {
                retVal = new CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy).proceed();
            }
            retVal = processReturnType(proxy, target, method, retVal);
            return retVal;
        }
    }

    private static Object processReturnType(Object proxy, Object target, Method method, Object returnValue) {
        Class<?> returnType = method.getReturnType();
        if (returnValue == null && returnType != Void.TYPE && returnType.isPrimitive()) {
            throw new AopInvocationException("Null return value from advice does not match primitive return type for: " + method);
        }
        return returnValue;
    }

    private static Object invokeMethod(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        try {
            return methodProxy.invoke(target, args);
        } catch (CodeGenerationException ex) {
            CglibMethodInvocation.logFastClassGenerationFailure(method);
            return AopUtils.invokeJoinpointUsingReflection(target, method, args);
        }
    }

    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {
        private final MethodProxy methodProxy;

        public CglibMethodInvocation(Object proxy, Object target, Method method, Object[] arguments, Class<?> targetClass, List<Object> interceptorsAndDynamicMethodMatchers, MethodProxy methodProxy) {
            super(proxy, target, method, arguments, targetClass, interceptorsAndDynamicMethodMatchers);
            this.methodProxy = methodProxy;
        }

        @Override
        public Object proceed() throws Throwable {
            try {
                return super.proceed();
            } catch (CodeGenerationException e) {
                throw new RuntimeException();
            }
        }

        static void logFastClassGenerationFailure(Method method) {
            if (log.isDebugEnabled()) {
                log.debug("Failed to generate CGLIB fast class for method: {}", method);
            }
        }
    }
}
