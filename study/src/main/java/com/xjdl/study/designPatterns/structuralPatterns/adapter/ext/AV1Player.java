package com.xjdl.study.designPatterns.structuralPatterns.adapter.ext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AV1Player implements AdvancedMediaPlayer {
    @Override
    public AV1Player playAV1(String name) {
        log.info("Playing AV1 from {}", name);
        return this;
    }

    @Override
    public AV1Player playHEVC(String name) {
        return this;
    }
}
