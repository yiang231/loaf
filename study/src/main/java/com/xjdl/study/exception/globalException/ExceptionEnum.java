package com.xjdl.study.exception.globalException;

import lombok.extern.slf4j.Slf4j;

/**
 * 异常处理枚举类
 */
@Slf4j
public enum ExceptionEnum implements BaseErrorInfoInterface{
    SUCCESS("2000", "accept success"),
    NOT_FOUND("4000", "resource is not found"),
    SERVER_BUSY("5000", "server is busying");
     private final String resultCode;
     private final String resultMsg;

    ExceptionEnum(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public String getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }
}
