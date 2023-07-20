package com.xjdl.study.designPatterns.structuralPatterns.proxy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class UseProxyImage {
    @Test
    void test() {
        Image image = new ProxyImage("a.png");
//        Image image = new ProxyImage("b.jpg");
//        Image image = new RealImage("xiu");

        // 创建了真实对象，调用的是真实对象的方法
        image.display();
        image.display();
        image.display();
    }
}
