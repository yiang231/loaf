package com.xjdl.study.designPatterns.structuralPatterns.adapter.adapter;

import com.xjdl.study.designPatterns.structuralPatterns.adapter.basic.MediaPlayer;
import com.xjdl.study.designPatterns.structuralPatterns.adapter.ext.AV1Player;
import com.xjdl.study.designPatterns.structuralPatterns.adapter.ext.AdvancedMediaPlayer;
import com.xjdl.study.designPatterns.structuralPatterns.adapter.ext.Dance;
import com.xjdl.study.designPatterns.structuralPatterns.adapter.ext.Excited;
import com.xjdl.study.designPatterns.structuralPatterns.adapter.ext.HEVCPlayer;
import lombok.extern.slf4j.Slf4j;

/**
 * 新接口适配旧接口，在旧接口中创建新接口的实现来完成功能
 */
@Slf4j
public class MediaAdapter implements MediaPlayer {
    AdvancedMediaPlayer advancedMediaPlayer;
    Excited excited;

    @Override
    public void play(String type, String name) {
        if (type.equalsIgnoreCase("AV1")) {
            advancedMediaPlayer = new AV1Player().playAV1(name);
        } else if (type.equalsIgnoreCase("HEVC")) {
            advancedMediaPlayer = new HEVCPlayer().playHEVC(name);
        }
        excited = new Dance().dance(name);
    }
}
