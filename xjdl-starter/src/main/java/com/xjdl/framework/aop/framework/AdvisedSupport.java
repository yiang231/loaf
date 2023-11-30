package com.xjdl.framework.aop.framework;

import com.xjdl.framework.aop.MethodMatcher;
import com.xjdl.framework.aop.target.TargetSource;
import lombok.Setter;
import org.aopalliance.intercept.MethodInterceptor;

@Setter
public class AdvisedSupport {
    private boolean proxyTargetClass = false;
    private TargetSource targetSource;
    private MethodInterceptor methodInterceptor;
    private MethodMatcher methodMatcher;

    public boolean isProxyTargetClass() {
        return this.proxyTargetClass;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }
}
