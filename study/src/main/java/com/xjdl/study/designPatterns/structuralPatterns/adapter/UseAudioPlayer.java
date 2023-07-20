package com.xjdl.study.designPatterns.structuralPatterns.adapter;

import com.xjdl.study.designPatterns.structuralPatterns.adapter.basic.AudioPlayer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class UseAudioPlayer {
    @Test
    void test() {
        AudioPlayer audioPlayer = new AudioPlayer();

        audioPlayer.play("MP3", "a.MP3");
        audioPlayer.play("AK47", "d.AK47");

        audioPlayer.play("AV1", "b.AV1");
        audioPlayer.play("HEVC", "c.HEVC");
    }
}
