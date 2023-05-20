package com.xjdl.study.annotation;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@PersonAnnotation(age = 33)
public class AnnotationTest<E> {
	@PersonAnnotation(name = "d31a", age = 32)
	E[] args;

	@SafeVarargs
	public AnnotationTest(E... args) {
		log.info("AnnotationTest构造器被调用，参数为 {}", args);
	}

	@SafeVarargs
	public static <T> void staticMethod(T... array) {
		log.info("staticMethod 最先被调用");
		for (T item : array) {
			log.info("{} {}", item.getClass().getSimpleName(), item);
		}
	}

	public static void main(String[] args) {
		AnnotationTest.staticMethod("staticArg1", "staticArg2");

		AnnotationTest<String> annotationTest = new AnnotationTest<>();
		annotationTest.deprecated();
		annotationTest.finalMethod("finalArg1", "finalArg2");
		annotationTest.print();

		FunctionalInterfaceAnnotation functionalInterfaceAnnotation = () -> log.info("@FunctionalInterface 重写函数式接口声明的run()方法被调用");
		functionalInterfaceAnnotation.run();
		log.info(functionalInterfaceAnnotation.defaultMethod());
		log.info(FunctionalInterfaceAnnotation.staticMethod());
	}

	@Deprecated
	@PersonAnnotation(name = "zhangsangege", age = 43)
	public void print() {
		try {
			log.info("{}{}应用了{}指定注解", getClass().getSimpleName(), getClass().isAnnotationPresent(PersonAnnotation.class) ? "" : "没有", getClass().getAnnotation(PersonAnnotation.class));
			Arrays.stream(getClass().getAnnotations()).forEach(o -> log.info("获取类上的所有注解信息 {}", o));
			log.info("获取类上的指定注解信息 {}", getClass().getAnnotation(PersonAnnotation.class));

			Field argsField = getClass().getDeclaredField("args");
			argsField.setAccessible(true);
			log.info("获取属性上的指定注解信息 {}", argsField.getAnnotation(PersonAnnotation.class)); // 只支持一个

			Field[] declaredFields = getClass().getDeclaredFields();
			Arrays.stream(declaredFields).forEach(o -> log.info("获取所有的有注解的属性 {}", o));

			Method printMethod = getClass().getDeclaredMethod("print");
			Arrays.stream(printMethod.getAnnotations()).forEach(o -> log.info("获取指定方法上的注解信息 {}", o));
		} catch (NoSuchFieldException | NoSuchMethodException e) {
			log.error(e.getMessage(), e);
		}
	}

	@Deprecated // 过时的
	@SuppressWarnings("deprecated") // 警告压制
	public void deprecated() {
		log.info("Deprecated 代表过时的");
	}

	@SafeVarargs
	public final <T> void finalMethod(T... array) {
		for (T item : array) {
			log.info("{} {}", item.getClass().getSimpleName(), item);
		}
	}
}
