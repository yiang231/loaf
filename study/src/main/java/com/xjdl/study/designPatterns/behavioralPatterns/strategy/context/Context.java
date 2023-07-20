package com.xjdl.study.designPatterns.behavioralPatterns.strategy.context;

import com.xjdl.study.designPatterns.behavioralPatterns.strategy.abstractStrategy.Strategy;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller
 * 维护对策略对象的引用
 */
@Slf4j
public class Context {
    private final Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public int calculate(int a, int b) {
        return strategy.operation(a, b);
    }
}
