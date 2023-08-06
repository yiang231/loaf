package com.xjdl.study.designPatterns.behavioralPatterns.state;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class UseState {
    @Test
    void test() {
        Context context = new Context();

        // 执行某个状态的方法，同时改变 Context 中状态的属性
        new StartState().doAction(context);
        log.info("{}", context.getState());

        new StopState().doAction(context);
        log.info("{}", context.getState());
    }
}
