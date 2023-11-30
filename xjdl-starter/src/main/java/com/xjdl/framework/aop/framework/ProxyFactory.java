package com.xjdl.framework.aop.framework;

public class ProxyFactory extends ProxyCreatorSupport {
    public ProxyFactory(AdvisedSupport config) {
        super.setProxyTargetClass(config.isProxyTargetClass());
        super.setTargetSource(config.getTargetSource());
        super.setMethodInterceptor(config.getMethodInterceptor());
        super.setMethodMatcher(config.getMethodMatcher());
    }

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    public Object getProxy(ClassLoader classLoader) {
        return createAopProxy().getProxy(classLoader);
    }
}
