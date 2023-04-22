package com.xjdl.study.variableParameter;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 可变参数，本质还是数组，可以直接用数组替换
 * 权限修饰符  返回值类型  方法名称（数据类型...  变量名称){}
 * 一个方法，只能有一个可变参数
 * 如果方法有多个参数，可变参数一定要放在末尾
 */
@Slf4j
public class VariableParameter {
	public static void main(String[] args) {
		log.info("{}", demo1(11, 22, 33, 44, 55));
		log.info("{}", demo2(23, true, "goushidan", "lisi"));
		for (Object item : demo2(23, true, "goushidan", "lisi")) {
			log.info("{}", item);
		}
	}

	private static int[] demo1(int... num) {
		for (int i : num) {
			log.info("{}", i);
		}
		log.info("可变参数长度为 {}", num.length);
		return num;
	}

	private static List<Object> demo2(int num, boolean flag, String... str) {
		List<Object> list = new ArrayList<>();
		Collections.addAll(list, num, flag, str);
		return list;
	}
}
