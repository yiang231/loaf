package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.factory.DisposableBean;
import com.xjdl.framework.beans.factory.ObjectFactory;
import com.xjdl.framework.beans.factory.config.SingletonBeanRegistry;
import com.xjdl.framework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
	private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);
	private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>(16);
	private final Map<String, DisposableBean> disposableBeans = new LinkedHashMap<>();

	@Override
	public void registerSingleton(String beanName, Object singletonObject) {
		assert beanName != null : "Bean name must not be null";
		assert singletonObject != null : "Singleton object must not be null";
		synchronized (this.singletonObjects) {
			Object oldObject = this.singletonObjects.get(beanName);
			if (oldObject != null) {
				throw new IllegalStateException("Could not register object [" + singletonObject +
						"] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
			}
			addSingleton(beanName, singletonObject);
		}
	}

	protected void addSingleton(String beanName, Object singletonObject) {
		synchronized (this.singletonObjects) {
			this.singletonObjects.put(beanName, singletonObject);
			this.singletonFactories.remove(beanName);
			this.earlySingletonObjects.remove(beanName);
		}
	}

	protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
		assert singletonFactory != null : "Singleton factory must not be null";
		synchronized (this.singletonObjects) {
			if (!this.singletonObjects.containsKey(beanName)) {
				this.singletonFactories.put(beanName, singletonFactory);
				this.earlySingletonObjects.remove(beanName);
			}
		}
	}

	@Override
	public Object getSingleton(String beanName) {
		return getSingleton(beanName, true);
	}

	protected Object getSingleton(String beanName, boolean allowEarlyReference) {
		Object singletonObject = this.singletonObjects.get(beanName);
		if (singletonObject == null) {
			singletonObject = this.earlySingletonObjects.get(beanName);
			if (singletonObject == null && allowEarlyReference) {
				synchronized (this.singletonObjects) {
					singletonObject = this.singletonObjects.get(beanName);
					if (singletonObject == null) {
						singletonObject = this.earlySingletonObjects.get(beanName);
						if (singletonObject == null) {
							ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
							if (singletonFactory != null) {
								singletonObject = singletonFactory.getObject();
								this.earlySingletonObjects.put(beanName, singletonObject);
								this.singletonFactories.remove(beanName);
							}
						}
					}
				}
			}
		}
		return singletonObject;
	}

	protected void removeSingleton(String beanName) {
		synchronized (this.singletonObjects) {
			this.singletonObjects.remove(beanName);
			this.singletonFactories.remove(beanName);
			this.earlySingletonObjects.remove(beanName);
		}
	}

	@Override
	public boolean containsSingleton(String beanName) {
		return this.singletonObjects.containsKey(beanName);
	}

	public void registerDisposableBean(String beanName, DisposableBean bean) {
		synchronized (this.disposableBeans) {
			this.disposableBeans.put(beanName, bean);
		}
	}

	public void destroySingletons() {
		if (log.isTraceEnabled()) {
			log.trace("Destroying singletons in {}", this);
		}
		String[] disposableBeanNames;
		synchronized (this.disposableBeans) {
			disposableBeanNames = StringUtils.toStringArray(this.disposableBeans.keySet());
		}
		for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
			destroySingleton(disposableBeanNames[i]);
		}
		clearSingletonCache();
	}

	protected void clearSingletonCache() {
		synchronized (this.singletonObjects) {
			this.singletonObjects.clear();
			this.singletonFactories.clear();
			this.earlySingletonObjects.clear();
		}
	}

	public void destroySingleton(String beanName) {
		removeSingleton(beanName);
		DisposableBean disposableBean;
		synchronized (this.disposableBeans) {
			disposableBean = this.disposableBeans.remove(beanName);
		}
		destroyBean(beanName, disposableBean);
	}

	protected void destroyBean(String beanName, DisposableBean bean) {
		if (bean != null) {
			try {
				bean.destroy();
			} catch (Throwable ex) {
				if (log.isWarnEnabled()) {
					log.warn("Destruction of bean with name '{}' threw an exception", beanName, ex);
				}
			}
		}
	}

	@Override
	public final Object getSingletonMutex() {
		return this.singletonObjects;
	}
}
