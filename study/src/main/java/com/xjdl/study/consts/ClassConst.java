package com.xjdl.study.consts;

import lombok.extern.slf4j.Slf4j;

/**
 * 相比接口的常量定义方式，推荐这种方式，编译后字节码文件里是方法，不是常量值，保证代码的动态性
 * <p>
 * 类定义常量
 */
@Slf4j
public class ClassConst {
    public static void main(String[] args) {
        log.info("{}", ClassConstDemo.getTrue());
        log.info("{}", ClassConstDemo.getFalse());
    }
}

final class ClassConstDemo {
    private static final int TRUE = 1;
    private static final int FALSE = 0;

    public static int getTrue() {
        return TRUE;
    }

    public static int getFalse() {
        return FALSE;
    }
}
