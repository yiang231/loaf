package com.xjdl.framework.aop.framework;

import com.xjdl.framework.util.ClassUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.core.CodeGenerationException;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodProxy;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 基于 CGLib 的动态代理
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
            log.trace("Creating CGLIB proxy: " + this.advised.getTargetSource());
        }
        try {
            Enhancer enhancer = createEnhancer();
            if (classLoader != null) {
                enhancer.setClassLoader(classLoader);
            }
            enhancer.setSuperclass(advised.getTargetSource().getTarget().getClass());
            enhancer.setInterfaces(advised.getTargetSource().getTargetClass());
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

    private static class DynamicAdvisedInterceptor implements net.sf.cglib.proxy.MethodInterceptor {

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
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            CglibMethodInvocation methodInvocation = new CglibMethodInvocation(advised.getTargetSource().getTarget(), method, objects, methodProxy);
            boolean matches = advised.getMethodMatcher().matches(method, advised.getTargetSource().getTarget().getClass());
            if (matches) {
                // 方法被切入
                return advised.getMethodInterceptor().invoke(methodInvocation);
            }
            return methodInvocation.proceed();
        }
    }

    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {
        private final MethodProxy methodProxy;

        public CglibMethodInvocation(Object target, Method method, Object[] arguments, MethodProxy methodProxy) {
            super(target, method, arguments);
            this.methodProxy = methodProxy;
        }

        @Override
        public Object proceed() throws Throwable {
            return this.methodProxy.invoke(this.target, this.arguments);
        }
    }
}
