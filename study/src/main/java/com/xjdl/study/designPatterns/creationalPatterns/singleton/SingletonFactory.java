package com.xjdl.study.designPatterns.creationalPatterns.singleton;

public class SingletonFactory {

    public static Singleton6 getInstance() {
        return EnumSingleton.Singleton.getInstance();
    }

    /**
     * 内部枚举类
     */
    private enum EnumSingleton {
        Singleton;
        private Singleton6 singleton;

        //枚举类的构造方法在类加载时被实例化
        private EnumSingleton() {
            singleton = new Singleton6();
        }

        public Singleton6 getInstance() {
            return singleton;
        }
    }
}

class Singleton6 {
    public Singleton6() {
    }
}

