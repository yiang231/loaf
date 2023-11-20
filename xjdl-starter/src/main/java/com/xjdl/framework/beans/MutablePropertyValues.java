package com.xjdl.framework.beans;

import java.util.ArrayList;
import java.util.List;

public class MutablePropertyValues implements PropertyValues {

	private final List<PropertyValue> propertyValueList;

	public MutablePropertyValues() {
		this.propertyValueList = new ArrayList<>(0);
	}

	@Override
	public void addPropertyValue(PropertyValue pv) {
		this.propertyValueList.add(pv);
	}

	@Override
	public PropertyValue[] getPropertyValues() {
		return this.propertyValueList.toArray(new PropertyValue[0]);
	}

	@Override
	public PropertyValue getPropertyValue(String propertyName) {
		for (PropertyValue pv : this.propertyValueList) {
			if (pv.getName().equals(propertyName)) {
				return pv;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "MutablePropertyValues{" +
				"propertyValueList=" + propertyValueList +
				'}';
	}
}
