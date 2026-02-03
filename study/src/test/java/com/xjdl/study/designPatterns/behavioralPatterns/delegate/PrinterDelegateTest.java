package com.xjdl.study.designPatterns.behavioralPatterns.delegate;

import org.junit.jupiter.api.Test;

class PrinterDelegateTest {
    @Test
    void print() {
        PrinterDelegate pd_canon = new PrinterDelegate(new CanonPrinter());
        PrinterDelegate pd_hp = new PrinterDelegate(new HpPrinter());
        PrinterDelegate pd_epson = new PrinterDelegate(new EpsonPrinter());

        pd_canon.print("Hello");
        pd_hp.print("World");
        pd_epson.print("Hello");
    }
}