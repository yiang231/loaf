package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.BeanFactory;
import com.xjdl.framework.beans.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class CglibSubclassingInstantiationStrategy extends SimpleInstantiationStrategy {
	@Override
	public Object instantiate(BeanDefinition bd, String beanName, BeanFactory owner) throws BeansException {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(bd.getBeanClass());
		enhancer.setCallback((MethodInterceptor) (obj, method, argsTemp, proxy) -> proxy.invokeSuper(obj, argsTemp));
		return enhancer.create();
	}
}
