package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.PropertyValue;
import com.xjdl.framework.beans.factory.BeanFactoryAware;
import com.xjdl.framework.beans.factory.BeanNameAware;
import com.xjdl.framework.beans.factory.ComponentFactory;
import com.xjdl.framework.beans.factory.InitializingBean;
import com.xjdl.framework.beans.factory.config.BeanPostProcessor;
import com.xjdl.framework.beans.factory.config.BeanReference;
import com.xjdl.framework.context.annotation.ScopeType;
import com.xjdl.framework.exception.FrameException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractComponentFactory implements ComponentFactory {
	/**
	 * 组件名和Bean定义对象
	 */
	protected Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
	/**
	 * 单例对象池
	 */
	protected Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>();
	/**
	 * 实现了 BeanPostProcessor 的组件
	 */
	protected List<String> beanPostProcessors = new ArrayList<String>();
	/**
	 * Scope 为 SINGLETON 的组件
	 */
	protected List<String> singletonComponentName = new ArrayList<String>();

	public Map<String, Object> getSingletonObjects() {
		return singletonObjects;
	}

	public List<String> getBeanPostProcessors() {
		return beanPostProcessors;
	}

	public List<String> getSingletonComponentName() {
		return singletonComponentName;
	}

	public Map<String, BeanDefinition> getBeanDefinitionMap() {
		return beanDefinitionMap;
	}

	/**
	 * 将 BeanDefinitionReader 里的 registryBeanDefinition 注入到 ComponentFactory 中
	 */
	public void registerBeanDefinition(String componentName, BeanDefinition beanDefinition) {
		this.registerSingletonComponent(componentName, beanDefinition);
		this.registerBeanPostProcessor(componentName, beanDefinition);
		this.beanDefinitionMap.put(componentName, beanDefinition);
	}

	private void registerSingletonComponent(String componentName, BeanDefinition beanDefinition) {
		if (beanDefinition.getScope().equals(ScopeType.singleton)) {
			this.singletonComponentName.add(componentName);
		}
	}

	public void registerBeanPostProcessor(String componentName, BeanDefinition beanDefinition) {
		if (BeanPostProcessor.class.isAssignableFrom(beanDefinition.getBeanClass())) {
			// TODO Bean的后置处理是在初始化的阶段进行的，最好的方法是直接以对象的形式存入，但是对于多例的组件来说不太合适
			this.beanPostProcessors.add(componentName);
		}
	}

	@Override
	public Object getComponent(String componentName) {
		BeanDefinition beanDefinition = beanDefinitionMap.get(componentName);
		if (beanDefinition == null) {
			throw new FrameException("The component named [" + componentName + "] is undefined.");
		}
		Object component = beanDefinition.getComponent();
		if (component == null) {
			if (beanDefinition.getScope().equals(ScopeType.singleton)) {
				component = singletonObjects.get(componentName);
				if (component == null) {
					component = doCreateBean(beanDefinition);
					singletonObjects.put(componentName, component);
					beanDefinition.setComponent(component);
				}
				return component;
			}
			// 多例下直接创建
			component = doCreateBean(beanDefinition);
		}
		return component;
	}

	private Object initializeComponent(Object component, String componentName) {
		this.initializeBeanAware(component, componentName);
		// 初始化前的操作
		boolean beanPostProcessorsSupport = beanPostProcessors.contains(componentName);
		if (beanPostProcessorsSupport) {
			component = ((BeanPostProcessor) component).postProcessBeforeInitialize(componentName, component);
		}
		// 初始化机制
		if (component instanceof InitializingBean) {
			((InitializingBean) component).afterPropertiesSet();
		}
		// 初始化后的操作，AOP支持的基石
		if (beanPostProcessorsSupport) {
			component = ((BeanPostProcessor) component).postProcessAfterInitialize(componentName, component);
		}
		return component;
	}

	/**
	 * Aware 回调机制，在初始化阶段完成
	 */
	private void initializeBeanAware(Object component, String componentName) {
		if (component instanceof BeanNameAware) {
			((BeanNameAware) component).setBeanName(componentName);
		}
		if (component instanceof BeanFactoryAware) {
			((BeanFactoryAware) component).setBeanFactory(this);
		}
	}

	public Object doCreateBean(BeanDefinition beanDefinition) {
		Object component = instanceComponent(beanDefinition);
		component = initializeComponent(component, beanDefinition.getComponentName());
		return component;
	}

	public Object instanceComponent(BeanDefinition beanDefinition) {
		Object component;
		try {
			component = beanDefinition.getBeanClass().newInstance();
		} catch (Exception e) {
			throw new FrameException(e);
		}
		return component;
	}

	/**
	 * 扫描 XML 文件方式的属性注入，暂不调用
	 */
	protected void applyPropertyValues(Object bean, BeanDefinition mbd) {
		try {
			for (PropertyValue propertyValue : mbd.getPropertyValues().getPropertyValues()) {
				Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
				declaredField.setAccessible(true);
				Object value = propertyValue.getValue();
				if (value instanceof BeanReference) {
					BeanReference beanReference = (BeanReference) value;
					value = getComponent(beanReference.getName());
				}
				declaredField.set(bean, value);
			}
		} catch (Exception e) {
			throw new FrameException(e);
		}
	}

//    public List getBeansForType(Class<?> type) throws Exception {
//        List beans = new ArrayList<Object>();
//        for (String componentName : beanDefinitionMap.keySet()) {
//            Class<?> beanClass = beanDefinitionMap.get(componentName).getBeanClass();
//            if (type.isAssignableFrom(beanClass)) {
//                beans.add(beanClass);
//            }
//        }
//        return beans;
//    }
}
