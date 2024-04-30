package com.xjdl.framework.aop.framework;

import com.xjdl.framework.core.NativeDetector;

import java.io.Serializable;
import java.lang.reflect.Proxy;

/**
 * 工厂模式实践
 */
public class DefaultAopProxyFactory implements AopProxyFactory, Serializable {
    @Override
    public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
        if (NativeDetector.inNativeImage() && (config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config))) {
            Class<?> targetClass = config.getTargetSource().getClass();
            if (targetClass == null) {
                throw new AopConfigException("TargetSource cannot determine target class: Either an interface or a target is required for proxy creation.");
            }
            if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
                return new JdkDynamicAopProxy(config);
            }
            return new CglibAopProxy(config);
        } else {
            return new JdkDynamicAopProxy(config);
        }
    }

    private boolean hasNoUserSuppliedProxyInterfaces(AdvisedSupport config) {
        Class<?>[] ifcs = config.getTargetSource().getTargetClass().getInterfaces();
        return (ifcs.length == 0 || (ifcs.length == 1));
    }
}
