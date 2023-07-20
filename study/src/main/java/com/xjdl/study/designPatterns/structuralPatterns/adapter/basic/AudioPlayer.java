package com.xjdl.study.designPatterns.structuralPatterns.adapter.basic;

import com.xjdl.study.designPatterns.structuralPatterns.adapter.adapter.MediaAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AudioPlayer implements MediaPlayer {
    MediaAdapter mediaAdapter;

    @Override
    public void play(String type, String name) {
        if (type.equalsIgnoreCase("MP3")) {
            // 默认功能
            log.info("Playing MP3 from {}", name);
        } else if (type.equalsIgnoreCase("AV1") || type.equalsIgnoreCase("HEVC")) {
            // 调用适配器进行功能扩展
            mediaAdapter = new MediaAdapter();
            mediaAdapter.play(type, name);
        } else {
            log.error("{}", "Invalid Media Type !");
        }
    }
}
