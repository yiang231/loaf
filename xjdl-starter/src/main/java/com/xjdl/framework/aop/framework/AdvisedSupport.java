package com.xjdl.framework.aop.framework;

import com.xjdl.framework.aop.MethodMatcher;
import com.xjdl.framework.aop.target.TargetSource;
import lombok.Getter;
import lombok.Setter;
import org.aopalliance.intercept.MethodInterceptor;

@Getter
@Setter
public class AdvisedSupport {
    private TargetSource targetSource;
    private MethodInterceptor methodInterceptor;
    private MethodMatcher methodMatcher;
}
