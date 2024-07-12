package com.xjdl.framework.aop.framework;

import com.xjdl.framework.aop.Advisor;
import com.xjdl.framework.aop.MethodMatcher;
import com.xjdl.framework.aop.TargetSource;
import com.xjdl.framework.util.CollectionUtils;
import lombok.Getter;
import lombok.Setter;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class AdvisedSupport implements Advised {
	private boolean proxyTargetClass = false;
	private TargetSource targetSource;
	private MethodInterceptor methodInterceptor;
	private MethodMatcher methodMatcher;
	AdvisorChainFactory advisorChainFactory = new DefaultAdvisorChainFactory();
	private transient Map<MethodCacheKey, List<Object>> methodCache;
	private List<Advisor> advisors = new ArrayList<>();

	public AdvisedSupport() {
		this.methodCache = new ConcurrentHashMap<>(32);
	}

	public void addAdvisors(Advisor... advisors) {
		addAdvisors(Arrays.asList(advisors));
	}

	public void addAdvisors(Collection<Advisor> advisors) {
		if (!CollectionUtils.isEmpty(advisors)) {
			for (Advisor advisor : advisors) {
				assert null != advisor : "Advisor must not be null";
				this.advisors.add(advisor);
			}
			adviceChanged();
		}
	}

	@Override
	public void removeAdvisor(int index) throws AopConfigException {
		if (index < 0 || index > this.advisors.size() - 1) {
			throw new AopConfigException("Advisor index " + index + " is out of bounds: " +
					"This configuration only has " + this.advisors.size() + " advisors.");
		}
		Advisor advisor = this.advisors.remove(index);
		adviceChanged();
	}

	public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) {
		MethodCacheKey cacheKey = new MethodCacheKey(method);
		List<Object> cached = this.methodCache.get(cacheKey);
		if (cached == null) {
			cached = this.advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(this, method, targetClass);
			this.methodCache.put(cacheKey, cached);
		}
		return cached;
	}

	@Override
	public final Advisor[] getAdvisors() {
		return this.advisors.toArray(new Advisor[0]);
	}

	protected void adviceChanged() {
		this.methodCache.clear();
	}

	public void setAdvisorChainFactory(AdvisorChainFactory advisorChainFactory) {
		assert null != advisorChainFactory : "AdvisorChainFactory must not be null";
		this.advisorChainFactory = advisorChainFactory;
	}

	private static final class MethodCacheKey implements Comparable<MethodCacheKey> {
		private final Method method;
		private final int hashCode;

		public MethodCacheKey(Method method) {
			this.method = method;
			this.hashCode = method.hashCode();
		}

		@Override
		public boolean equals(Object other) {
			return (this == other || (other instanceof MethodCacheKey &&
					this.method == ((MethodCacheKey) other).method));
		}

		@Override
		public int hashCode() {
			return this.hashCode;
		}

		@Override
		public String toString() {
			return this.method.toString();
		}

		@Override
		public int compareTo(MethodCacheKey other) {
			int result = this.method.getName().compareTo(other.method.getName());
			if (result == 0) {
				result = this.method.toString().compareTo(other.method.toString());
			}
			return result;
		}
	}
}
