package com.xjdl.study.designPatterns.creationalPatterns.singleton;

/**
 * 使用内部枚举的方式
 */
public class Singleton4 {
    /**
     * 私有化构造器
     */
    private Singleton4() {
    }

    public static Singleton4 getInstance() {
        return EnumSingleton.SINGLETON.getInstance();
    }

    public static void main(String[] args) {
        Singleton4 instance = Singleton4.getInstance();
    }

    /**
     * 内部枚举类
     */
    private enum EnumSingleton {
        /**
         * 枚举变量
         */
        SINGLETON;
        /**
         * 定义对象实例，不初始化
         */
        private Singleton4 instance;

        /**
         * 枚举类的构造方法在类加载是被实例化
         * Java虚拟机会保证枚举类型不能被反射并且构造函数只被执行一次
         */
        private EnumSingleton() {
            instance = new Singleton4();
        }

        private Singleton4 getInstance() {
            return instance;
        }
    }
}
