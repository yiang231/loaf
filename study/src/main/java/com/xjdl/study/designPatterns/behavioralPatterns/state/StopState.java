package com.xjdl.study.designPatterns.behavioralPatterns.state;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StopState implements State {
    @Override
    public void doAction(Context context) {
        log.info("{}", "Stop state...");
        context.setState(this);
    }

    @Override
    public String toString() {
        return "Stop state";
    }
}
