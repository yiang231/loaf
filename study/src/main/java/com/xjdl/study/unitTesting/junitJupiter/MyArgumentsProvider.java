package com.xjdl.study.unitTesting.junitJupiter;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

/**
 * 自定义的，可重用的 ArgumentsProvider
 */
public class MyArgumentsProvider implements ArgumentsProvider {
	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
		return Stream.of("foo", "bar").map(Arguments::of);
	}
}
