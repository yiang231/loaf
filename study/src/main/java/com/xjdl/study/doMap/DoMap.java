package com.xjdl.study.doMap;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Map的遍历
 */
@Slf4j
public class DoMap {
	public Map<String, String> map;

	{
		// 注意，无序存储
		map = new HashMap<String, String>() {{
			put("5", "5");
			put("=", "=");
			put("a", "1");
			put("b", "2");
			put("c", "3");
			put("d", "4");
		}};
	}

	/**
	 * 修改map中的元素
	 * 改变value，直接setValue()重新赋值，或者put()相同的key，新的value
	 * 改变key，删除后新增元素
	 */
	@Test
	void method7() {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (entry.getKey().equals("5")) {
				entry.setValue("新的value");
//				map.put(entry.getKey(), "新的value");
			}
		}
		log.info("修改value的值后 {}", map);

		map.keySet().removeIf(key -> Objects.equals(key, "5"));
		map.put("修改后key的值", "原来的value值");
		log.info("修改key的值后 {}", map);
	}

	/**
	 * stream
	 * 使用filter过滤掉数据
	 */
	@Test
	public void method6() {
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
	 */
	@Test
	public void method5() {
//        map.keySet().removeIf(key -> Objects.equals(key, "a"));
//		map.forEach((key, value) -> log.info("{} === {}", key, value));
		map.keySet().forEach(log::info);
		map.values().forEach(log::info);
	}

	/**
	 * 不推荐使用，性能差
	 */
	@Test
	public void method4() {
		for (String key : map.keySet()) {
			log.info("{} === {}", key, map.get(key));
		}
	}

	/**
	 * 使用迭代器遍历
	 * 遍历时使用remove()删除元素是线程安全的
	 */
	@Test
	public void method3() {
		Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
//            iterator.remove();
			log.info("{} === {}", entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 只获取key或者是value
	 */
	@Test
	public void method2() {
		for (String item : map.keySet()) {
			log.info(item);
		}
		for (String item : map.values()) {
			log.info(item);
		}
	}

	/**
	 * 使用entrySet，同时获取key或者是value
	 */
	@Test
	public void method1() {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			log.info("{} === {}", entry.getKey(), entry.getValue());
		}
	}
}
