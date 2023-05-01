package com.xjdl.study.doMap;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Map的遍历
 */
@Slf4j
public class DoMap {
	public static void main(String[] args) {
		// 注意，无序存储
		HashMap<String, String> map = new HashMap<String, String>() {{
			put("5", "5");
			put("=", "=");
			put("a", "1");
			put("b", "2");
			put("c", "3");
			put("d", "4");
		}};

		method1(map);
		method2(map);
		method3(map);
		method4(map);
		method5(map);
		method6(map);
	}

	/**
	 * stream
	 * 使用filter过滤掉数据
	 *
	 * @param map
	 */
	private static void method6(HashMap<String, String> map) {
		map.entrySet().stream()
//                .filter(item -> !Objects.equals(item.getKey(), "b"))
				.forEach(entry -> {
					log.info(entry.getKey());
					log.info(entry.getValue());
				});
	}

	/**
	 * λ表达式
	 * 循环中删除是不安全的
	 * 应该先删除再循环
	 *
	 * @param map
	 */
	private static void method5(HashMap<String, String> map) {
//        map.keySet().removeIf(key -> Objects.equals(key, "a"));
//		map.forEach((key, value) -> log.info("{} === {}", key, value));
		map.keySet().forEach(log::info);
		map.values().forEach(log::info);
	}

	/**
	 * 不推荐使用，性能差
	 *
	 * @param map
	 */
	private static void method4(HashMap<String, String> map) {
		for (String key : map.keySet()) {
			log.info("{} === {}", key, map.get(key));
		}
	}

	/**
	 * 使用迭代器遍历
	 * 遍历时使用remove()删除元素是线程安全的
	 *
	 * @param map
	 */
	private static void method3(HashMap<String, String> map) {
		Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
//            iterator.remove();
			log.info("{} === {}", entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 只获取key或者是value
	 *
	 * @param map
	 */
	private static void method2(HashMap<String, String> map) {
		for (String item : map.keySet()) {
			log.info(item);
		}
		for (String item : map.values()) {
			log.info(item);
		}
	}

	/**
	 * 使用entrySet，同时获取key或者是value
	 *
	 * @param map
	 */
	private static void method1(HashMap<String, String> map) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			log.info("{} === {}", entry.getKey(), entry.getValue());
		}
	}
}
