package com.xjdl.study.designPatterns.behavioralPatterns.template.common;

import com.xjdl.study.designPatterns.behavioralPatterns.template.Game;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Basketball extends Game {
    @Override
    protected void initialize() {
        log.info("{}", "Basketball Game initialize...");
    }

    @Override
    protected void start() {
        log.info("{}", "Basketball Game start...");
    }

    @Override
    protected void end() {
        log.info("{}", "Basketball Game end...");
    }

    @Override
    protected void remake() {
        log.info("{}", "Basketball Game remake...");
    }
}
