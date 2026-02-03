package com.xjdl.study.designPatterns.behavioralPatterns.delegate;

import lombok.extern.slf4j.Slf4j;

/**
 * 委托模式，类似策略模式和代理模式的结合
 */
@Slf4j
public class PrinterDelegate implements Printer {
    private final Printer printer;

    public PrinterDelegate(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void print(String msg) {
        System.out.println("prepare to print ===>");
        printer.print(msg);
        System.out.println("end to print <===");
    }
}
