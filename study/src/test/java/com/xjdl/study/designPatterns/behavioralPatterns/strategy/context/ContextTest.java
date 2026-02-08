package com.xjdl.study.designPatterns.behavioralPatterns.strategy.context;

import com.xjdl.study.designPatterns.behavioralPatterns.strategy.concreteStrategy.Divide;
import com.xjdl.study.designPatterns.behavioralPatterns.strategy.concreteStrategy.Subtract;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ContextTest {
    @Test
    void test() {
        Context divide = new Context(new Divide());
        log.info("Divide Strategy result {}", divide.calculate(32, 2));

        Context subtract = new Context(new Subtract());
        log.info("Subtract Strategy result {}", subtract.calculate(9, 3));
    }
}