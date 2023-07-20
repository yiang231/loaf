package com.xjdl.study.designPatterns.behavioralPatterns.chainOfResponsibility;

import org.apache.logging.log4j.spi.StandardLevel;

public abstract class AbstractLogger {
    public static int ERROR = StandardLevel.ERROR.intLevel();
    public static int WARN = StandardLevel.WARN.intLevel();
    public static int INFO = StandardLevel.INFO.intLevel();
    protected int intLevel;
    /**
     * 聚合本抽象类
     */
    protected AbstractLogger nextLogger;

    public void setNextLogger(AbstractLogger nextLogger) {
        this.nextLogger = nextLogger;
    }

    public void logMsg(int intLevel, String msg) {
        if (this.intLevel <= intLevel) {
            write(msg);
        }
        // 传递给下一个实体类
        if (nextLogger != null) {
            nextLogger.logMsg(intLevel, msg);
        }
    }

    abstract protected void write(String msg);
}
