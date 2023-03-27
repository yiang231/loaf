package com.dl.test.pattern.singletonPattern;

/**
 * 懒汉式加同步锁以及双检查
 */
public class Singleton2 {
	/**
	 * 默认不初始化
	 */
	private static Singleton2 instance = null;

	/**
	 * 私有化构造器
	 */
	private Singleton2() {
	}

	/**
	 * 返回实例对象
	 *
	 * @return
	 */
	public static Singleton2 getInstance() {
		if (instance == null) {
			synchronized (Singleton2.class) {
				if (instance == null) {
					instance = new Singleton2();
				}
			}
		}
		return instance;
	}

	public static void main(String[] args) {
		Singleton2 instance = Singleton2.getInstance();
	}
}
