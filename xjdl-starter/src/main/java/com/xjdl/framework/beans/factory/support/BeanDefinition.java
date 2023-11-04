package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.PropertyValues;
import com.xjdl.framework.context.annotation.ScopeType;

public class BeanDefinition {
	/**
	 * 组件名
	 */
	private String componentName;
	/**
	 * 全类名
	 */
	private String clazzName;
	/**
	 * 组件本身
	 * <p>
	 * TODO 单例的组件才会在 BeanDefinition 有此属性吗？目前来说是的
	 */
	private Object component;
	/**
	 * 作用域
	 */
	private ScopeType scope;
	/**
	 * Bean 的 Class 信息
	 */
	private Class<?> beanClass;
	/**
	 * 内部 Fields，使用XML文件方式注入对象属性时使用
	 */
	private PropertyValues propertyValues = new PropertyValues();

	public Class<?> getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	public PropertyValues getPropertyValues() {
		return propertyValues;
	}

	public void setPropertyValues(PropertyValues propertyValues) {
		this.propertyValues = propertyValues;
	}

	public ScopeType getScope() {
		return scope;
	}

	public void setScope(ScopeType scope) {
		this.scope = scope;
	}

	public Object getComponent() {
		return component;
	}

	public void setComponent(Object component) {
		this.component = component;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	@Override
	public String toString() {
		return "BeanDefinition{" +
				"componentName='" + componentName + '\'' +
				", clazzName='" + clazzName + '\'' +
				", scope=" + scope +
				", beanClass=" + (beanClass != null ? "true" : "false") +
				", component=" + component +
				", propertyValues=" + propertyValues +
				'}';
	}
}

