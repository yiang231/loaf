package com.xjdl.study.designPatterns.behavioralPatterns.state;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartState implements State {
    @Override
    public void doAction(Context context) {
        log.info("{}", "Start state...");
        context.setState(this);
    }

    @Override
    public String toString() {
        return "Start state";
    }
}
