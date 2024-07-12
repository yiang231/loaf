package com.xjdl.framework.beans;

public interface PropertyValues {
    PropertyValue[] getPropertyValues();

    PropertyValue getPropertyValue(String propertyName);

    void addPropertyValue(PropertyValue pv);

    boolean contains(String propertyName);

    boolean isEmpty();
}
