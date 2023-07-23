package com.xjdl.beans.factory.config;

import lombok.Data;

// interface
@Data
public class BeanDefinition {
	private String scope;
	private Class<?> type;
}
