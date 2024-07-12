package com.xjdl.framework.aop.framework;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProxyCreatorSupport extends AdvisedSupport {
    private AopProxyFactory aopProxyFactory;

    public ProxyCreatorSupport() {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

    protected final synchronized AopProxy createAopProxy() {
        return getAopProxyFactory().createAopProxy(this);
    }
}
