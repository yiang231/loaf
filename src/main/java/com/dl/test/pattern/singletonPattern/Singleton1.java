package com.dl.test.pattern.singletonPattern;

/**
 * 饿汉式
 */
public class Singleton1 {
    /**
     * 初始化实力对象
     */
    private static Singleton1 singleton1 = new Singleton1();

    /**
     * 私有化构造器
     */
    private Singleton1() {
    }

    /**
     * 返回初始化好的实例对象
     *
     * @return
     */
    public static Singleton1 getInstance() {
        return singleton1;
    }

    public static void main(String[] args) {
        Singleton1 instance = Singleton1.getInstance();
    }
}
