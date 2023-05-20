package com.xjdl.study.unitTesting.junitJupiter;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicContainer;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.ThrowingConsumer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 加了@TestFactory注解的方法必须返回DynamicNode实例的Stream，Collection，Iterable或Iterator
 */
@Slf4j
public class DynamicTestsDemo {

	// This will result in a JUnitException!

	/**
	 * 第一种方法返回无效的返回类型。由于在编译时无法检测到无效的返回类型，因此在运行时检测并抛出JUnitException异常
	 */
	@TestFactory
	List<String> dynamicTestsWithInvalidReturnType() {
		return Arrays.asList("Hello");
	}

	@TestFactory
	Collection<DynamicTest> dynamicTestsFromCollection() {
		return Arrays.asList(
				DynamicTest.dynamicTest("1st dynamic test", () -> Assertions.assertTrue(true)),
				DynamicTest.dynamicTest("2nd dynamic test", () -> Assertions.assertEquals(4, 2 * 2))
		);
	}

	@TestFactory
	Iterable<DynamicTest> dynamicTestsFromIterable() {
		return Arrays.asList(
				DynamicTest.dynamicTest("3rd dynamic test", () -> Assertions.assertTrue(true)),
				DynamicTest.dynamicTest("4th dynamic test", () -> Assertions.assertEquals(4, 2 * 2))
		);
	}

	@TestFactory
	Iterator<DynamicTest> dynamicTestsFromIterator() {
		return Arrays.asList(
				DynamicTest.dynamicTest("5th dynamic test", () -> Assertions.assertTrue(true)),
				DynamicTest.dynamicTest("6th dynamic test", () -> Assertions.assertEquals(4, 2 * 2))
		).iterator();
	}

	@TestFactory
	Stream<DynamicTest> dynamicTestsFromStream() {
		return Stream.of("A", "B", "C")
				.map(str -> DynamicTest.dynamicTest("test" + str, () -> { /* ... */ }));
	}

	@TestFactory
	Stream<DynamicTest> dynamicTestsFromIntStream() {
		// Generates tests for the first 10 even integers.
		return IntStream.iterate(0, n -> n + 2).limit(10)
				.mapToObj(n -> DynamicTest.dynamicTest("test" + n, () -> Assertions.assertEquals(0, n % 2)));
	}

	/**
	 * 真正的动态
	 */
	@TestFactory
	Stream<DynamicTest> generateRandomNumberOfTests() {

		// Generates random positive integers between 0 and 100 until
		// a number evenly divisible by 7 is encountered.
		Iterator<Integer> inputGenerator = new Iterator<Integer>() {

			final Random random = new Random();
			int current;

			@Override
			public boolean hasNext() {
				current = random.nextInt(100);
				return current % 7 != 0;
			}

			@Override
			public Integer next() {
				return current;
			}
		};

		// Generates display names like: input:5, input:37, input:85, etc.
		Function<Integer, String> displayNameGenerator = (input) -> "input:" + input;

		// Executes tests based on the current input value.
		ThrowingConsumer<Integer> testExecutor = (input) -> Assertions.assertTrue(input % 7 != 0);

		// Returns a stream of dynamic tests.
		return DynamicTest.stream(inputGenerator, displayNameGenerator, testExecutor);
	}

	/**
	 * 使用DynamicContainer生成动态测试的嵌套层次结构
	 */
	@TestFactory
	Stream<DynamicNode> dynamicTestsWithContainers() {
		return Stream.of("A", "B", "C")
				.map(input -> DynamicContainer.dynamicContainer("Container " + input, Stream.of(
						DynamicTest.dynamicTest("not null", () -> Assertions.assertNotNull(input)),
						DynamicContainer.dynamicContainer("properties", Stream.of(
								DynamicTest.dynamicTest("length > 0", () -> Assertions.assertTrue(input.length() > 0)),
								DynamicTest.dynamicTest("not empty", () -> Assertions.assertFalse(input.isEmpty()))
						))
				)));
	}
}
