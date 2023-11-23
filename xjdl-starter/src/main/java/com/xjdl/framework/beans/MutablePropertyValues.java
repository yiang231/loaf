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
		for (int i = 0; i < this.propertyValueList.size(); i++) {
			PropertyValue currentPv = this.propertyValueList.get(i);
			if (currentPv.getName().equals(pv.getName())) {
				// 覆盖操作
				this.propertyValueList.set(i, pv);
			}
		}
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
	public boolean contains(String propertyName) {
		return getPropertyValue(propertyName) != null;
	}

	@Override
	public String toString() {
		return "MutablePropertyValues{" +
				"propertyValueList=" + propertyValueList +
				'}';
	}
}
