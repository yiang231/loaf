package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.factory.DisposableBean;
import com.xjdl.framework.beans.factory.config.BeanDefinition;
import com.xjdl.framework.util.BeanUtils;
import com.xjdl.framework.util.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
class DisposableBeanAdapter implements DisposableBean {
	private final Object bean;

	private final String beanName;
	private String destroyMethodName;

	public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
		this.bean = bean;
		this.beanName = beanName;
		this.destroyMethodName = beanDefinition.getDestroyMethodName();
	}

	@Override
	public void destroy() {
		if (this.destroyMethodName != null) {
			Method destroyMethod = determineDestroyMethod(this.destroyMethodName);
			if (destroyMethod != null) {
				invokeCustomDestroyMethod(destroyMethod);
			}
		}
	}

	private Method determineDestroyMethod(String name) {
		try {
			return findDestroyMethod(name);
		} catch (IllegalArgumentException e) {
			throw new BeanDefinitionValidationException("Could not find unique destroy method on bean with name '" +
					this.beanName + ": " + e.getMessage(), e);
		}
	}

	private Method findDestroyMethod(String name) {
		return BeanUtils.findMethod(this.bean.getClass(), name);
	}

	private void invokeCustomDestroyMethod(final Method destroyMethod) {
		int paramCount = destroyMethod.getParameterCount();
		final Object[] args = new Object[paramCount];
		if (paramCount == 1) {
			args[0] = Boolean.TRUE;
		}
		if (log.isTraceEnabled()) {
			log.trace("Invoking custom destroy method '" + this.destroyMethodName +
					"' on bean with name '" + this.beanName + "'");
		}
		try {
			ReflectionUtils.makeAccessible(destroyMethod);
			destroyMethod.invoke(this.bean, args);
		} catch (IllegalAccessException | InvocationTargetException ex) {
			if (log.isWarnEnabled()) {
				log.warn("Failed to invoke custom destroy method '" + this.destroyMethodName +
						"' on bean with name '" + this.beanName + "'", ex);
			}
		}
	}
}
