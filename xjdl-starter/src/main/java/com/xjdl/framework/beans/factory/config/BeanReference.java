package com.xjdl.framework.beans.factory.config;

/**
 * 声明依赖另一个 bean 的对象引用
 */
public class BeanReference {

	private String beanName;

	private Object bean;

	public BeanReference(String beanName) {
		this.beanName = beanName;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}
}
