package com.xjdl.study.designPatterns.behavioralPatterns.template;

public abstract class Game {
    protected abstract void initialize();

    protected abstract void start();

    protected abstract void end();

    protected abstract void remake();

    /**
     * 模板方法使用 final 修饰
     * 模板骨架：封装不变的部分
     */
    public final void play() {
        initialize();
        start();
        end();
    }
}
