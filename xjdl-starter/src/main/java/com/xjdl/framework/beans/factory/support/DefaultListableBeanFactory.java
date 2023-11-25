package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.BeanDefinitionStoreException;
import com.xjdl.framework.beans.factory.BeanFactory;
import com.xjdl.framework.beans.factory.NoSuchBeanDefinitionException;
import com.xjdl.framework.beans.factory.config.BeanDefinition;
import com.xjdl.framework.beans.factory.config.ConfigurableListableBeanFactory;
import com.xjdl.framework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {
	private static final Map<String, Reference<DefaultListableBeanFactory>> serializableFactories = new ConcurrentHashMap<>(8);
	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
	private final List<String> beanDefinitionNames = new ArrayList<>();
	private String serializationId;

	public DefaultListableBeanFactory(BeanFactory parentBeanFactory) {
		super(parentBeanFactory);
	}

	public DefaultListableBeanFactory() {
		super();
	}

	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionStoreException {
		this.beanDefinitionMap.put(beanName, beanDefinition);
		this.beanDefinitionNames.add(beanName);
	}

	@Override
	public void preInstantiateSingletons() throws BeansException {
		if (log.isTraceEnabled()) {
			log.trace("Pre-instantiating singletons in " + this);
		}
		List<String> beanNames = new ArrayList<>(this.beanDefinitionNames);
		for (String beanName : beanNames) {
			getBean(beanName);
		}
	}

	@Override
	public BeanDefinition getBeanDefinition(String beanName) {
		BeanDefinition bd = this.beanDefinitionMap.get(beanName);
		if (bd == null) {
			if (log.isTraceEnabled()) {
				log.trace("No bean named '" + beanName + "' found in " + this);
			}
			throw new NoSuchBeanDefinitionException(beanName);
		}
		return bd;
	}

	@Override
	public boolean containsBeanDefinition(String beanName) {
		return beanDefinitionMap.containsKey(beanName);
	}

	@Override
	public String[] getBeanDefinitionNames() {
		return StringUtils.toStringArray(this.beanDefinitionNames);
	}

	/**
	 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#getBeansOfType(Class, boolean, boolean)
	 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#doGetBeanNamesForType(org.springframework.core.ResolvableType, boolean, boolean)
	 */
	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
		Map<String, T> result = new HashMap<>();
		beanDefinitionMap.forEach((beanName, beanDefinition) -> {
			Class<?> beanClass = beanDefinition.getBeanClass();
			if (type.isAssignableFrom(beanClass)) {
				T bean = (T) super.getBean(beanName);
				result.put(beanName, bean);
			}
		});
		return result;
	}

	@Override
	public int getBeanDefinitionCount() {
		return beanDefinitionMap.size();
	}

	public void setSerializationId(String serializationId) {
		if (serializationId != null) {
			serializableFactories.put(serializationId, new WeakReference<>(this));
		} else if (this.serializationId != null) {
			serializableFactories.remove(this.serializationId);
		}
		this.serializationId = serializationId;
	}

	@Override
	public void destroySingletons() {
		super.destroySingletons();
	}

	@Override
	public String[] getBeanNamesForType(Class<?> type) {
		return getBeansOfType(type).keySet().toArray(new String[0]);
	}
}
