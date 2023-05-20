package com.xjdl.study.unitTesting.junitJupiter;

import lombok.Data;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

/**
 * 字段聚合为对象
 */
@Data
public class JunitObj implements ArgumentsAggregator {
	private Integer num;
	private String str;
	private JunitEnum junitEnum;

	@Override
	public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
		this.num = accessor.getInteger(0);
		this.str = accessor.getString(1);
		this.junitEnum = accessor.get(2, JunitEnum.class);
		return this;
	}
}
