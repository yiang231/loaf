package com.xjdl.study.designPatterns.creationalPatterns.singleton;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class SingletonTest {
    @Test
    void test() {
        Singleton4 instance0 = Singleton4.getInstance();
        Singleton4 instance1 = Singleton4.getInstance();
        Singleton4 instance2 = Singleton4.getInstance();
        Singleton4 instance3 = Singleton4.getInstance();

        log.info("instance0 = {}", instance0);
        log.info("instance1 = {}", instance1);
        log.info("instance2 = {}", instance2);
        log.info("instance3 = {}", instance3);
    }
}
