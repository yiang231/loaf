package com.xjdl.study.consts.enumConst;

import lombok.extern.slf4j.Slf4j;

enum EnumConstDemoTest2 {
    SUN("Sunday", 7),
    MON("Monday", 1),
    TUE("Tuesday", 2),
    WED("Wednesday", 3),
    THU("Thursday", 4),
    FRI("Friday", 5),
    SAT("Saturday", 6);

    private final String daily;
    private final int code;

    EnumConstDemoTest2(String daily, int code) {
        this.daily = daily;
        this.code = code;
    }

    public String getDaily() {
        return daily;
    }

    public int getCode() {
        return code;
    }
}

/**
 * 枚举变量添加属性
 */
@Slf4j
public class EnumConstDemo2 {
    /**
     * 遍历枚举类
     * 以及枚举类的一些方法
     */
    public static void main(String[] args) {
        for (EnumConstDemoTest2 value : EnumConstDemoTest2.values()) {
            log.info(value.getCode() + " -> " + value.getDaily());
            log.info("传入一个和枚举常量相同的字符串，返回枚举常量，当前是 {}", EnumConstDemoTest2.valueOf(value.getDaily().substring(0,3).toUpperCase()));
            log.info("当前枚举对象的索引是 {}", value.ordinal());
        }
    }
}
