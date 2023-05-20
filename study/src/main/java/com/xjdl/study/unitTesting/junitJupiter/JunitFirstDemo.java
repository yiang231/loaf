package com.xjdl.study.unitTesting.junitJupiter;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

//@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
@DisplayName("第一个测试用例")
// 指定测试顺序
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// 测试名称生成器 下划线变成空格
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
// 默认的测试名称生成器
//@IndicativeSentencesGeneration(separator = IndicativeSentencesGeneration.DEFAULT_SEPARATOR, generator = DisplayNameGenerator.Standard.class)
public class JunitFirstDemo {
	@BeforeAll
	public static void beforeAll() {
		log.info("beforeAll");
	}

	@AfterAll
	public static void afterAll() {
		log.info("afterAll");
	}

	/**
	 * 方法数据源-单个参数
	 * 必须是静态方法
	 * 必须返回一个Stream，Iterable，Iterator或者参数数组
	 */
	static Stream<String> methodSource() {
		return Stream.of("methodSource parameter");
	}

	/**
	 * 方法数据源-多个参数
	 */
	static Stream<Arguments> stringIntAndListProvider() {
		return Stream.of(
				Arguments.of("foo", 1, Arrays.asList("a", "b")),
				Arguments.of("bar", 2, Arrays.asList("x", "y"))
		);
	}

	@BeforeEach
	public void setUp() {
		log.info("beforeEach");
	}

	@AfterEach
	public void tearDown() {
		log.info("afterEach");
	}

	@Test
	@DisplayName("第一个测试")
	@Order(1)
		// 第一个执行
	void testFirst() {
		log.info("第一个测试");
	}

	@Test
	@DisplayName("第二个测试")
	@Order(2)
		// 第二个执行
	void testSecond() {
		log.info("第二个测试");
	}

	@DisplayName("重复性测试")
	@RepeatedTest(value = 10, name = "{displayName} 第 {currentRepetition} 总共 {totalRepetitions}")
//    @RepeatedTest(value = 10, name = RepeatedTest.LONG_DISPLAY_NAME)
	// 并发执行
	@Execution(ExecutionMode.CONCURRENT)
	void testRepeated() {
		log.info("重复性测试");
	}

	@Test
	@DisplayName("要跳过的测试")
	// void com.xjdl.study.unitTesting.junit5.JunitFirstDemo.testSkip() is @Disabled
	@Disabled
	void testSkip() {
		log.info("要跳过的测试");
	}

	/**
	 * 断言测试
	 */
	@Test
	@DisplayName("断言测试")
	void testGroupAssertions() {
		int[] array1 = {0, 1, 2, 3, 4};
		int[] array2 = {4, 3, 2, 1, 0};
		Assertions.assertAll("array"
				, () -> Assertions.assertArrayEquals(array1, array2)
				, () -> Assertions.assertEquals(array1[1], array2[1])
				, () -> Assertions.assertEquals(array1[1], array2[1]));
	}

	/**
	 * 执行时间超时测试
	 */
	@Test
	@DisplayName("执行时间超时测试")
	void testAssertTimeoutPreemptively() {
//        Assertions.assertTimeoutPreemptively(Duration.of(1, ChronoUnit.SECONDS)
		Assertions.assertTimeoutPreemptively(Duration.ofSeconds(1)
				, () -> {
					log.info("任务超时测试");
					Thread.sleep(1500);
				}, "任务已超时");
	}

	/**
	 * 假设断言
	 */
	@Test
	@DisplayName("假设断言")
	void testAssumptions() {
		String numberOfProcessors = System.getenv("NUMBER_OF_PROCESSORS");
		log.info("NUMBER_OF_PROCESSORS={}", numberOfProcessors);
		Assumptions.assumeTrue(numberOfProcessors.equals("4"));

		Assumptions.assumingThat(false, () -> {
			Assertions.assertEquals("4", numberOfProcessors);
		});
	}

	/**
	 * 异常测试
	 */
	@Test
	@DisplayName("异常测试")
	void testAssertThrows() {
		NullPointerException exception = Assertions.assertThrows(NullPointerException.class
				, () -> {
					log.info("异常测试");
//                    throw new ArithmeticException("手动抛出一个不匹配的异常");
					throw new NullPointerException("手动抛出一个匹配的异常");
				}, "异常不匹配");
		Assertions.assertEquals("手动抛出一个不匹配的异常", exception.getMessage(), "异常信息匹配失败");
	}

	/**
	 * 参数化测试-ValueSource单参数
	 */
	@ParameterizedTest
	@DisplayName("参数化测试-ValueSource单参数")
	@NullAndEmptySource
	@ValueSource(strings = {"a", "b", "c"})
	void testValueSource(String str) {
		log.info(str);
	}

	/**
	 * 参数化测试-Enum枚举参数
	 */
	@ParameterizedTest
	@DisplayName("参数化测试-枚举参数")
	// 排除枚举值
	@EnumSource(names = {"I", "T"}, mode = EnumSource.Mode.EXCLUDE)
// 	指定枚举值，mode可以省略
//	@EnumSource(names = {"I", "T"}, mode = EnumSource.Mode.INCLUDE)
	void testEnumSource(JunitEnum junitEnum) {
		log.info("{}", junitEnum);
	}

	/**
	 * 参数化测试-方法数据源参数
	 * 引用格式 QualifierName # MethodName
	 */
	@ParameterizedTest
	@DisplayName("参数化测试-方法数据源单参数")
	@MethodSource("com.xjdl.study.unitTesting.junitJupiter.JunitFirstDemo#methodSource")
	void testWithSingleArgMethodSource(String candidate) {
		log.info("{}", candidate);
	}

	/**
	 * 参数化测试-方法数据源多参数
	 */
	@ParameterizedTest
	@DisplayName("参数化测试-方法数据源多参数")
	@MethodSource("com.xjdl.study.unitTesting.junitJupiter.JunitFirstDemo#stringIntAndListProvider")
	void testWithMultiArgMethodSource(String str, int num, List<String> list) {
		Assertions.assertEquals(3, str.length());
		Assertions.assertTrue(num >= 1 && num <= 2);
		Assertions.assertEquals(2, list.size());
	}

	/**
	 * 参数化测试-CSV多参数
	 * comma-separated values
	 */
	// 自定义参数化测试 测试名称 类似 @RepeatedTest(name = "")
	@ParameterizedTest(name = "{index} ==> p1=''{0}'', p2={1}")
	@DisplayName("参数化测试-CSV多参数")
	@CsvSource(value = {"1|One", "2|Two", "3|Three", "4|NIL"}, nullValues = "NIL", delimiterString = "|")
	void testCsvSource(int num, String str) {
		log.info("num = {}, str = {}", num, str);
	}

	/**
	 * 参数化测试-字段聚合(Argument Aggregation)
	 */
	@ParameterizedTest
	@DisplayName("参数化测试-字段聚合(Argument Aggregation)")
	@CsvSource({"1, One, J", "2, Two, U", "3, Three, N", "4, Four, I", "5, Five, T"})
	void testArgumentsAccessor(ArgumentsAccessor arguments) {
		log.info("num = {}, str = {}, junitEnum = {}", arguments.get(0), arguments.getString(1), arguments.get(2, JunitEnum.class));
	}

	/**
	 * 参数化测试-字段聚合对象(Argument Aggregation) @AggregateWith
	 */
	@ParameterizedTest
	@DisplayName("参数化测试-字段聚合对象(Argument Aggregation) @AggregateWith")
	@CsvSource({"1, One, J", "2, Two, U", "3, Three, N", "4, Four, I", "5, Five, T"})
	void testArgumentsAccessor(@AggregateWith(JunitObj.class) JunitObj junitObj) {
		log.info("{}", junitObj);
	}

	/**
	 * 参数化测试-使用自定义注解字段聚合对象(Argument Aggregation) @AggregateWithJunitObj
	 */
	@ParameterizedTest
	@DisplayName("参数化测试-使用自定义注解字段聚合对象(Argument Aggregation) @AggregateWithJunitObj")
	@CsvSource({"1, One, J", "2, Two, U", "3, Three, N", "4, Four, I", "5, Five, T"})
	void testArgumentsAccessorAnnotation(@AggregateWithJunitObj JunitObj junitObj) {
		log.info("{}", junitObj);
	}

	/**
	 * 参数化测试-CSV文件入参
	 */
	@DisplayName("参数化测试-CSV文件多条记录入参")
	@ParameterizedTest
	// 文件应该在测试目录下的resources下，和main有相同的目录结构。这里在main中测试，所以文件位置在main里面的resources下
	@CsvFileSource(resources = "/csv/password.csv", numLinesToSkip = 0)
	void testCsvFileSource(String name, String url, String username, String password) {
		log.info("{}, {}, {}, {}", name, url, username, password);
	}

	/**
	 * 参数化测试-自定义参数源
	 */
	@DisplayName("参数化测试-自定义参数源")
	@ParameterizedTest
	@ArgumentsSource(MyArgumentsProvider.class)
	void testWithArgumentsSource(String argument) {
		log.info(argument);
		Assertions.assertNotNull(argument);
	}

	/**
	 * 参数隐式转换
	 */
	@DisplayName("参数隐式转换-int型自动转为double型入参")
	@ParameterizedTest
	@ValueSource(ints = {1, 2, 3})
	void testArgumentConversionTest(double candidate) {
		log.info("{}", candidate);
	}

	/**
	 * 自定义参数显示转换
	 */
	@DisplayName("参数显式转换-TimeUnit 转 String")
	@ParameterizedTest
	@EnumSource(TimeUnit.class)
	void testWithExplicitArgumentConversion(@ConvertWith(ToStringArgumentConverter.class) String argument) {
		log.info(argument);
		Assertions.assertNotNull(TimeUnit.valueOf(argument));
	}

	@Nested
	@DisplayName("第一个内嵌测试类")
	class FirstNestTest {
		@Test
		@DisplayName("第一个内嵌测试")
		void testFirstNest() {
			log.info("第一个内嵌测试");
		}
	}

	@Nested
	@DisplayName("第二个要跳过的内嵌测试类")
	@Disabled
	class SecondNestTest {
		@Test
		@DisplayName("第二个要跳过的内嵌测试")
		void testSecondNest() {
			log.info("第二个要跳过的内嵌测试");
		}
	}
}
