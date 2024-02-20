package com.xjdl.framework.aop.framework.autoproxy;

import com.xjdl.framework.aop.Advisor;
import com.xjdl.framework.aop.ClassFilter;
import com.xjdl.framework.aop.Pointcut;
import com.xjdl.framework.aop.PointcutAdvisor;
import com.xjdl.framework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.xjdl.framework.aop.framework.AdvisedSupport;
import com.xjdl.framework.aop.framework.ProxyFactory;
import com.xjdl.framework.aop.target.TargetSource;
import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.PropertyValues;
import com.xjdl.framework.beans.factory.BeanClassLoaderAware;
import com.xjdl.framework.beans.factory.BeanFactory;
import com.xjdl.framework.beans.factory.BeanFactoryAware;
import com.xjdl.framework.beans.factory.config.BeanDefinition;
import com.xjdl.framework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.xjdl.framework.beans.factory.support.DefaultListableBeanFactory;
import com.xjdl.framework.util.ClassUtils;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.List;

@Slf4j
public abstract class AbstractAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware, BeanClassLoaderAware {
    private ClassLoader proxyClassLoader = ClassUtils.getDefaultClassLoader();
    private BeanFactory beanFactory;

    protected BeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    protected ClassLoader getProxyClassLoader() {
        return this.proxyClassLoader;
    }

    public void setProxyClassLoader(ClassLoader classLoader) {
        this.proxyClassLoader = classLoader;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.proxyClassLoader = classLoader;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (isInfrastructureClass(beanClass)) {
            return null;
        }
        TargetSource targetSource = getCustomTargetSource(beanClass, beanName);
        if (targetSource != null) {
            List<PointcutAdvisor> specificInterceptors = getAdvicesAndAdvisorsForBean(beanClass, beanName);
            Object proxy = createProxy(beanClass, beanName, specificInterceptors, targetSource);
            return proxy;
        }
        return null;
    }

    /**
     * 排除掉相关切面类，避免重复创建 AspectJExpressionPointcutAdvisor
     */
    protected boolean isInfrastructureClass(Class<?> beanClass) {
        boolean retVal = Advice.class.isAssignableFrom(beanClass) ||
                Pointcut.class.isAssignableFrom(beanClass) ||
                Advisor.class.isAssignableFrom(beanClass);
        if (retVal && log.isTraceEnabled()) {
            log.trace("Did not attempt to auto-proxy infrastructure class [" + beanClass.getName() + "]");
        }
        return retVal;
    }

    /**
     * 创建代理对象的必要条件，拥有 TargetSourceCreator，在这里会返回绝大部分的 null
     *
     * @see org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator#getCustomTargetSource(Class, String)
     */
    protected TargetSource getCustomTargetSource(Class<?> beanClass, String beanName) {
        if (this.beanFactory != null && this.beanFactory.containsBean(beanName)) {
            return getTargetSource(beanClass, beanName);
        }
        return null;
    }

    public final TargetSource getTargetSource(Class<?> beanClass, String beanName) {
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
        BeanDefinition beanDefinition = defaultListableBeanFactory.getBeanDefinition(beanName);
        Object instantiate = defaultListableBeanFactory.getInstantiationStrategy().instantiate(beanDefinition, beanName, defaultListableBeanFactory);
        return new TargetSource(instantiate);
    }

    protected Object createProxy(Class<?> beanClass, String beanName, List<PointcutAdvisor> specificInterceptors, TargetSource targetSource) {
        for (PointcutAdvisor advisor : specificInterceptors) {
            if (advisor instanceof AspectJExpressionPointcutAdvisor) {
                ClassFilter classFilter = advisor.getPointcut().getClassFilter();
                if (classFilter.matches(beanClass)) {
                    AdvisedSupport advisedSupport = new AdvisedSupport();
                    advisedSupport.setTargetSource(targetSource);
                    advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
                    advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
                    advisedSupport.setProxyTargetClass(true);
                    // 返回代理对象
                    return new ProxyFactory(advisedSupport).getProxy(getProxyClassLoader());
                }
            }
        }
        return null;
    }

    protected abstract List<PointcutAdvisor> getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName) throws BeansException;

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return pvs;
    }
}
