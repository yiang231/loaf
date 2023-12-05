package com.xjdl.framework.aop.framework.autoproxy;

import com.xjdl.framework.aop.Advisor;
import com.xjdl.framework.aop.PointcutAdvisor;
import com.xjdl.framework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.BeanFactory;
import com.xjdl.framework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractAdvisorAutoProxyCreator extends AbstractAutoProxyCreator {
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        super.setBeanFactory(beanFactory);
        if (!(beanFactory instanceof ConfigurableListableBeanFactory)) {
            throw new IllegalArgumentException(
                    "AdvisorAutoProxyCreator requires a ConfigurableListableBeanFactory: " + beanFactory);
        }
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    protected List<PointcutAdvisor> getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName) throws BeansException {
        List<PointcutAdvisor> advisors = findEligibleAdvisors(beanClass, beanName);
        return advisors;
    }

    protected List<PointcutAdvisor> findEligibleAdvisors(Class<?> beanClass, String beanName) {
        List<Advisor> candidateAdvisors = findCandidateAdvisors();
        List<PointcutAdvisor> eligibleAdvisors = findAdvisorsThatCanApply(candidateAdvisors);
        return eligibleAdvisors;
    }

    protected List<Advisor> findCandidateAdvisors() {
        return findAdvisorBeans();
    }

    public List<Advisor> findAdvisorBeans() {
        Map<String, Advisor> advisorMap = beanFactory.getBeansOfType(Advisor.class);
        return new ArrayList<>(advisorMap.values());
    }

    protected List<PointcutAdvisor> findAdvisorsThatCanApply(List<Advisor> candidateAdvisors) {
        if (candidateAdvisors.isEmpty()) {
            return null;
        }
        List<PointcutAdvisor> eligibleAdvisors = new ArrayList<>();
        for (Advisor candidate : candidateAdvisors) {
            if (candidate instanceof AspectJExpressionPointcutAdvisor) {
                eligibleAdvisors.add((PointcutAdvisor) candidate);
            }
        }
        return eligibleAdvisors;
    }
}
