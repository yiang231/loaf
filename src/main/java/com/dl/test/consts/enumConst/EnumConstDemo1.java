package com.dl.test.consts.enumConst;

import lombok.extern.slf4j.Slf4j;

enum EnumConstDemoTest1 {
    A, B, C, D, E
}

/**
 * 常规用法
 */
@Slf4j
public class EnumConstDemo1 {
    public static void main(String[] args) {
        switch (EnumConstDemoTest1.E) {
            case A:
                log.info("A");
                break;
            case B:
                log.info("B");
                break;
            case C:
                log.info("C");
                break;
            case D:
                log.info("D");
                break;
            case E:
                log.info("E");
                break;
        }
    }
}
