package com.xjdl.framework.beans;

public interface PropertyValues {
	PropertyValue[] getPropertyValues();

	PropertyValue getPropertyValue(String propertyName);

	void addPropertyValue(PropertyValue pv);
}
