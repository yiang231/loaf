package com.xjdl.framework.aop.framework.autoproxy;

import com.xjdl.framework.beans.factory.BeanNameAware;

public class DefaultAdvisorAutoProxyCreator extends AbstractAdvisorAutoProxyCreator implements BeanNameAware {
    public static final String SEPARATOR = ".";
    private String advisorBeanNamePrefix;

    public String getAdvisorBeanNamePrefix() {
        return this.advisorBeanNamePrefix;
    }

    public void setAdvisorBeanNamePrefix(String advisorBeanNamePrefix) {
        this.advisorBeanNamePrefix = advisorBeanNamePrefix;
    }

    @Override
    public void setBeanName(String name) {
        if (this.advisorBeanNamePrefix == null) {
            this.advisorBeanNamePrefix = name + SEPARATOR;
        }
    }
}
