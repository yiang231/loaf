package com.xjdl.framework.aop.aspectj;

import com.xjdl.framework.aop.Pointcut;
import com.xjdl.framework.aop.PointcutAdvisor;
import com.xjdl.framework.beans.factory.BeanFactory;
import com.xjdl.framework.beans.factory.BeanFactoryAware;
import org.aopalliance.aop.Advice;

import java.io.Serializable;

public class AspectJExpressionPointcutAdvisor implements BeanFactoryAware, PointcutAdvisor, Serializable {

	private final AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

	private Advice advice = EMPTY_ADVICE;

	@Override
	public Advice getAdvice() {
		return this.advice;
	}

	public void setAdvice(Advice advice) {
		this.advice = advice;
	}

	public String getExpression() {
		return this.pointcut.getExpression();
	}

	public void setExpression(String expression) {
		this.pointcut.setExpression(expression);
	}

	public String getLocation() {
		return this.pointcut.getLocation();
	}

	public void setLocation(String location) {
		this.pointcut.setLocation(location);
	}

	public void setParameterNames(String... names) {
		this.pointcut.setParameterNames(names);
	}

	public void setParameterTypes(Class<?>... types) {
		this.pointcut.setParameterTypes(types);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.pointcut.setBeanFactory(beanFactory);
	}

	@Override
	public Pointcut getPointcut() {
		return this.pointcut;
	}
}
