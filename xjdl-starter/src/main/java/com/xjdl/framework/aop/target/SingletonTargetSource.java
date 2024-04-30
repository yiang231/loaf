package com.xjdl.framework.aop.target;

import com.xjdl.framework.aop.TargetSource;
import com.xjdl.framework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import com.xjdl.framework.util.ObjectUtils;

import java.io.Serializable;

/**
 * 默认的 TargetSource
 * @see AbstractAutoProxyCreator#postProcessAfterInitialization(Object, String)
 */
public class SingletonTargetSource implements TargetSource, Serializable {
	private final Object target;

	public SingletonTargetSource(Object target) {
		if (target == null) {
			throw new IllegalArgumentException("Target object must not be null");
		}
		this.target = target;
	}

	@Override
	public Class<?> getTargetClass() {
		return this.target.getClass();
	}

	@Override
	public Object getTarget() {
		return this.target;
	}

	@Override
	public void releaseTarget(Object target) {
	}

	@Override
	public boolean isStatic() {
		return true;
	}

	@Override
	public boolean equals(Object other) {
		return (this == other || (other instanceof SingletonTargetSource &&
				this.target.equals(((SingletonTargetSource) other).target)));
	}

	@Override
	public int hashCode() {
		return this.target.hashCode();
	}

	@Override
	public String toString() {
		return "SingletonTargetSource for target object [" + ObjectUtils.identityToString(this.target) + "]";
	}
}
