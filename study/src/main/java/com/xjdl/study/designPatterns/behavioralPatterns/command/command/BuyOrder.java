package com.xjdl.study.designPatterns.behavioralPatterns.command.command;

import com.xjdl.study.designPatterns.behavioralPatterns.command.received.Stock;

/**
 * concrete command buy
 */
public class BuyOrder implements Order {
    Stock stock;

    public BuyOrder(Stock stock) {
        this.stock = stock;
    }

    @Override
    public void execute() {
        stock.buy();
    }
}
