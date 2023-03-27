package com.dl.test.convert;

/**
 * 面试题
 */
public class Test {
	public static void main(String[] args) {
		int x = Integer.MAX_VALUE;
		System.out.println(x);
		System.out.println(x + 1);
		if (x + 1 < x) {
			System.out.println(x);
		}
		System.out.println((x++) * 3);
	}
}
