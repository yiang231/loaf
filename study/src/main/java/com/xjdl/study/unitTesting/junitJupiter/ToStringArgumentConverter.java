package com.xjdl.study.unitTesting.junitJupiter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

/**
 * 显式参数转换器
 */
public class ToStringArgumentConverter extends SimpleArgumentConverter {
	@Override
	protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
		Assertions.assertEquals(String.class, targetType, "Can only convert to String");
		return String.valueOf(source);
	}
}
