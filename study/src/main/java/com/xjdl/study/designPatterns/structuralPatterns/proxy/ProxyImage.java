package com.xjdl.study.designPatterns.structuralPatterns.proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * 通过代理类访问真实类【被代理类】
 */
@Slf4j
public class ProxyImage implements Image {
    private RealImage realImage;
    private String imageName;

    public ProxyImage(String imageName) {
        this.imageName = imageName;
    }

    public String getImageName() {
        return this.imageName;
    }

    /**
     * 添加真实类的访问控制
     */
    @Override
    public void display() {
        if (realImage == null) {
            if (this.getImageName().equalsIgnoreCase("a.png")) {
                log.warn("create real {}", this.getImageName());
                realImage = new RealImage(imageName);
            } else {
                log.error("{}", "Access denied !");
                return;
            }
        }
        realImage.display();
    }
}
