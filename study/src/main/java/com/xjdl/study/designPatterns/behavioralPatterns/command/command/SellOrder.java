package com.xjdl.study.designPatterns.behavioralPatterns.command.command;

import com.xjdl.study.designPatterns.behavioralPatterns.command.command.Order;
import com.xjdl.study.designPatterns.behavioralPatterns.command.received.Stock;

/**
 * concrete command sell
 */
public class SellOrder implements Order {
    Stock stock;

    public SellOrder(Stock stock) {
        this.stock = stock;
    }

    @Override
    public void execute() {
        stock.sell();
    }
}
