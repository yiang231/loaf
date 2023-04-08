package com.dl.test.consts;

import lombok.extern.slf4j.Slf4j;

/**
 * 默认都是 public static final 修饰
 */
interface InterfaceConstDemo {
    int Sunday = 7;
    int Monday = 1;
    int Tuesday = 2;
    int Wednesday = 3;
    int Thursday = 4;
    int Friday = 5;
    int Saturday = 6;
}

/**
 * 接口定义常量
 */
@Slf4j
public class InterfaceConst {
    public static void main(String[] args) {
        log.info("{}", InterfaceConstDemo.Sunday);
        log.info("{}", InterfaceConstDemo.Monday);
        log.info("{}", InterfaceConstDemo.Tuesday);
        log.info("{}", InterfaceConstDemo.Wednesday);
        log.info("{}", InterfaceConstDemo.Thursday);
        log.info("{}", InterfaceConstDemo.Friday);
        log.info("{}", InterfaceConstDemo.Saturday);
    }
}
