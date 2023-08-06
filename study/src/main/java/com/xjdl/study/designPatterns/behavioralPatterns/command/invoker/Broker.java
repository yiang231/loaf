package com.xjdl.study.designPatterns.behavioralPatterns.command.invoker;

import com.xjdl.study.designPatterns.behavioralPatterns.command.command.Order;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * invoker
 */
@Slf4j
public class Broker {
    private List<Order> orders = new ArrayList<>();

    public void takeOrder(Order order) {
        orders.add(order);
    }

    public void placeOrders() {
        orders.forEach(Order::execute);
        orders.clear();
    }
}
