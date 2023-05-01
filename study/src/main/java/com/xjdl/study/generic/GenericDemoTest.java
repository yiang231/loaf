package com.xjdl.study.generic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 泛型测试类
 * 好处
 * 1. 保证类型的安全性
 * 2. 消除强制转换
 * 3. 避免不必要的拆装箱操作，减少性能损失
 * 4. 提高代码的复用性
 */
@Slf4j
public class GenericDemoTest {
	@Test
	public void test1() {
		GenericMethod<Integer, String> genericMethod = new GenericMethod<>();

		log.info("{}", genericMethod.print(21, "32"));
		log.info("{}", genericMethod.printE(22));
		log.info("{}", genericMethod.printV("23"));
	}

	@Test
	public void test2() {
		GenericClass<String> string = new GenericClass<>("GenericClass");
		GenericClass<Integer> integer = new GenericClass<>(123);

		log.info("{}", string);
		log.info("{}", integer);
	}

	@Test
	public void test3() {
		GenericInterfaceImpl genericInterface = new GenericInterfaceImpl();

		genericInterface.show("GenericInterface");
		genericInterface.show(21);
		genericInterface.show(21.36);
	}
}
