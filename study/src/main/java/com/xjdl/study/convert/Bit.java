package com.xjdl.study.convert;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 面试题
 */
@Slf4j
public class Bit {
	@Test
	void intValue() {
		int x = Integer.MAX_VALUE;
		log.info("{}", x);
		log.info("{}", x + 1);
		if (x + 1 < x) {
			log.info("{}", x);
		}
		log.info("{}", (x++) * 3);
	}

	@Test
	void bitCalc() {
		// 乘以 10
		log.info("{}", (-54 << 1) + (-54 << 3));

		// 除以 2
		log.info("{}", 10 >> 1);
		log.info("{}", 16 >> 1);

		// 乘以 2
		log.info("{}", 10 << 1);
		log.info("{}", 16 << 1);

		// 除以 16
		log.info("{}", 1024 >>> 4);

		// 乘以 1024
		log.info("{}", 1 << 10);

		// 是否相同
		log.info("{}", (32 ^ 32) == 0 ? true : false);
		log.info("{}", (32 ^ 23) == 0 ? true : false);

		// 奇偶性
		log.info("{}", 25 & 1);
		log.info("{}", 26 & 1);

		// 交换变量
		int a = 43, b = 7868;
		a ^= b;
		b ^= a;
		a ^= b;
		log.info("a = {}, b = {}", a, b);

		// 求余数，如果刚好是 2 的 n 次幂
		int c = 1 << 9;
		log.info("常规取余 {}", 53256235 % c);
		log.info("快速取余 {}", 53256235 & (c - 1));

		// 找出只出现一次的数字 a ^ a = 0 , a ^ 0 = a
		int[] arr = {26, 32, 78, 32, 26, 5, 5};
		int ret = arr[0];
		for (int i = 1; i < arr.length; i++) {
			ret ^= arr[i];
		}
		log.info("{}", ret);

		// 加解密
		int d = 1919180, key = 114514;
		log.info("加密 {}", d ^ key);
		log.info("解密 {}", (d ^ key) ^ key);

		// 数字与字符互转
		int e = 67;
		log.info("67 = {}, 115 = {}", (char) e, (char) (e ^ 48));
		for (int i = 33; i < 123; i++) {
			log.info("{} = {}", i, (char) i);
		}
	}
}
