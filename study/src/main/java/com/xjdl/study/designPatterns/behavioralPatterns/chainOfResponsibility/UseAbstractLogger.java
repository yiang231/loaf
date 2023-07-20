package com.xjdl.study.designPatterns.behavioralPatterns.chainOfResponsibility;

import com.xjdl.study.designPatterns.behavioralPatterns.chainOfResponsibility.staff.ErrorLogger;
import com.xjdl.study.designPatterns.behavioralPatterns.chainOfResponsibility.staff.InfoLogger;
import com.xjdl.study.designPatterns.behavioralPatterns.chainOfResponsibility.staff.WarnLogger;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class UseAbstractLogger {
    public static AbstractLogger getLoggerChain() {
        InfoLogger infoLogger = new InfoLogger(AbstractLogger.INFO);
        WarnLogger warnLogger = new WarnLogger(AbstractLogger.WARN);
        ErrorLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);

        infoLogger.setNextLogger(warnLogger);
        warnLogger.setNextLogger(errorLogger);

        return infoLogger;
    }

    @Test
    void test() {
        AbstractLogger logger = getLoggerChain();

        logger.logMsg(AbstractLogger.INFO, "info");
        logger.logMsg(AbstractLogger.WARN, "warn");
        logger.logMsg(AbstractLogger.ERROR, "error");
    }
}
