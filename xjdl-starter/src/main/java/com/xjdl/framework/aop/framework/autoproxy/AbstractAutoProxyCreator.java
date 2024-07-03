package com.xjdl.framework.aop.framework.autoproxy;

import com.xjdl.framework.aop.Advisor;
import com.xjdl.framework.aop.Pointcut;
import com.xjdl.framework.aop.TargetSource;
import com.xjdl.framework.aop.framework.ProxyFactory;
import com.xjdl.framework.aop.framework.adapter.AdvisorAdapterRegistry;
import com.xjdl.framework.aop.framework.adapter.GlobalAdvisorAdapterRegistry;
import com.xjdl.framework.aop.target.SingletonTargetSource;
import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.PropertyValues;
import com.xjdl.framework.beans.factory.BeanClassLoaderAware;
import com.xjdl.framework.beans.factory.BeanFactory;
import com.xjdl.framework.beans.factory.BeanFactoryAware;
import com.xjdl.framework.beans.factory.config.ConfigurableBeanFactory;
import com.xjdl.framework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.xjdl.framework.util.ClassUtils;
import com.xjdl.framework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.proxyClassLoader = classLoader;
    }
    private String[] interceptorNames = new String[0];
    private AdvisorAdapterRegistry advisorAdapterRegistry = GlobalAdvisorAdapterRegistry.getInstance();
    /**
     * 排除掉相关切面类，避免重复创建 AspectJExpressionPointcutAdvisor
     */
    protected boolean isInfrastructureClass(Class<?> beanClass) {
        boolean retVal = Advice.class.isAssignableFrom(beanClass) ||
                Pointcut.class.isAssignableFrom(beanClass) ||
                Advisor.class.isAssignableFrom(beanClass);
        if (retVal && log.isTraceEnabled()) {
			log.trace("Did not attempt to auto-proxy infrastructure class [{}]", beanClass.getName());
        }
        return retVal;
    }

    protected Object createProxy(Class<?> beanClass, String beanName, Object[] specificInterceptors, TargetSource targetSource) {
        Advisor[] advisors = buildAdvisors(beanName, specificInterceptors);
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvisors(advisors);
        proxyFactory.setTargetSource(targetSource);
//        proxyFactory.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
//        proxyFactory.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
        proxyFactory.setProxyTargetClass(true);
        return proxyFactory.getProxy(ClassUtils.getDefaultClassLoader());
    }

    protected Advisor[] buildAdvisors(String beanName, Object[] specificInterceptors) {
        Advisor[] commonInterceptors = resolveInterceptorNames();
        List<Object> allInterceptors = new ArrayList<>();
        if (specificInterceptors != null) {
            if (specificInterceptors.length > 0) {
                allInterceptors.addAll(Arrays.asList(specificInterceptors));
            }
            if (commonInterceptors.length > 0) {
                allInterceptors.addAll(Arrays.asList(commonInterceptors));
            }
        }
        if (log.isTraceEnabled()) {
            int nrOfCommonInterceptors = commonInterceptors.length;
            int nrOfSpecificInterceptors = (specificInterceptors != null ? specificInterceptors.length : 0);
            log.trace("Creating implicit proxy for bean '{}' with {} common interceptors and {} specific interceptors", beanName, nrOfCommonInterceptors, nrOfSpecificInterceptors);
        }

        Advisor[] advisors = new Advisor[allInterceptors.size()];
        for (int i = 0; i < allInterceptors.size(); i++) {
            advisors[i] = this.advisorAdapterRegistry.wrap(allInterceptors.get(i));
        }
        return advisors;
    }

    private Advisor[] resolveInterceptorNames() {
        BeanFactory bf = this.beanFactory;
        ConfigurableBeanFactory cbf = (bf instanceof ConfigurableBeanFactory ? (ConfigurableBeanFactory) bf : null);
        List<Advisor> advisors = new ArrayList<>();
        for (String beanName : this.interceptorNames) {
            if (cbf == null) {
                assert null != bf : "BeanFactory required for resolving interceptor names";
                Object next = bf.getBean(beanName);
                advisors.add(this.advisorAdapterRegistry.wrap(next));
            }
        }
        return advisors.toArray(new Advisor[0]);
    }

    protected abstract Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName) throws BeansException;

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return pvs;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean != null) {
            return wrapIfNecessary(bean, beanName);
        }
        return null;
    }

    protected Object wrapIfNecessary(Object bean, String beanName) {
        if (!StringUtils.hasLength(beanName)) {
            return bean;
        }
        if (isInfrastructureClass(bean.getClass())) {
            return bean;
        }
        Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(bean.getClass(), beanName);
        if (specificInterceptors != null) {
            return createProxy(bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean));
        }
        return bean;
    }
}
