package com.xjdl.study.designPatterns.structuralPatterns.adapter.ext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HEVCPlayer implements AdvancedMediaPlayer {
    @Override
    public HEVCPlayer playAV1(String name) {
        return this;
    }

    @Override
    public HEVCPlayer playHEVC(String name) {
        log.info("Playing HEVC from {}", name);
        return this;
    }
}
