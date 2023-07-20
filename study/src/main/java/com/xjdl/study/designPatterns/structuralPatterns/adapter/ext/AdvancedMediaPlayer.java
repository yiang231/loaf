package com.xjdl.study.designPatterns.structuralPatterns.adapter.ext;

public interface AdvancedMediaPlayer {
    AdvancedMediaPlayer playAV1(String name);

    AdvancedMediaPlayer playHEVC(String name);
}
