package com.xjdl.study.designPatterns.behavioralPatterns.template.common;

import com.xjdl.study.designPatterns.behavioralPatterns.template.Game;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Sing extends Game {
    @Override
    protected void initialize() {
        log.info("{}", "Sing Game initialize...");
    }

    @Override
    protected void start() {
        log.info("{}", "Sing Game start...");
    }

    @Override
    protected void end() {
        log.info("{}", "Sing Game end...");
    }

    @Override
    protected void remake() {
        log.info("{}", "Sing Game remake...");
    }
}
