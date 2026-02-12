package com.xjdl.study.designPatterns.behavioralPatterns.iterator;

import java.util.Iterator;

public class NumberIterator extends BaseIterator implements Iterable<Character> {
    public NumberIterator() {
        super(10, '0');
    }

    @Override
    public Iterator<Character> iterator() {
        // 为了在匿名类里能访问外部的 this，返回一个本地匿名类适配器，它实现 java.util.Iterator 并把调用转发给本实例
        NumberIterator outer = this;
        return new Iterator<Character>() {
            @Override
            public boolean hasNext() {
                return outer.handleBefore();
            }

            @Override
            public Character next() {
                Character c = outer.iterate();
                outer.handleAfter();
                return c;
            }
        };
    }
}