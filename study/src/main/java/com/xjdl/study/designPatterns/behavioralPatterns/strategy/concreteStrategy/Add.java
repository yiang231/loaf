package com.xjdl.study.designPatterns.behavioralPatterns.strategy.concreteStrategy;

import com.xjdl.study.designPatterns.behavioralPatterns.strategy.abstractStrategy.Strategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Add implements Strategy {
    @Override
    public int operation(int a, int b) {
        return a + b;
    }
}
