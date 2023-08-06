package com.xjdl.study.designPatterns.behavioralPatterns.command.received;

import lombok.extern.slf4j.Slf4j;

/**
 * received
 */
@Slf4j
public class Stock {
    public String name = "ABC";
    public int quantity = 10;

    public void buy() {
        log.info("Stock [ Name: {}, Quantity: {} ] bought", name, quantity);
    }

    public void sell() {
        log.info("Stock [ Name: {}, Quantity: {} ] sold", name, quantity);
    }
}
