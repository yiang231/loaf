package com.dl.test.pattern.singletonPattern;

/**
 * 使用静态内部类创建单例模式
 */
public class Singleton3 {
    /**
     * 私有化构造器
     */
    private Singleton3() {
    }

    /**
     * 返回实例对象
     *
     * @return
     */
    public static Singleton3 getInstance() {
        return InnerObject.instance;
    }

    public static void main(String[] args) {
        Singleton3 instance = Singleton3.getInstance();
    }

    /**
     * 使用静态内部类
     */
    private static class InnerObject {
        private static Singleton3 instance = new Singleton3();
    }
}
