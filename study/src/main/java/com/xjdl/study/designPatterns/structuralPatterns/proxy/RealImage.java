package com.xjdl.study.designPatterns.structuralPatterns.proxy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RealImage implements Image {
    private String imageName;

    public RealImage(String imageName) {
        this.imageName = imageName;
        loadFromDisk(imageName);
    }

    @Override
    public void display() {
        log.info("Displaying {}", imageName);
    }

    private void loadFromDisk(String imageName) {
        log.info("Loading {}", imageName);
    }
}
