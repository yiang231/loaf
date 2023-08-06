package com.xjdl.study.designPatterns.behavioralPatterns.command;

import com.xjdl.study.designPatterns.behavioralPatterns.command.command.BuyOrder;
import com.xjdl.study.designPatterns.behavioralPatterns.command.command.SellOrder;
import com.xjdl.study.designPatterns.behavioralPatterns.command.invoker.Broker;
import com.xjdl.study.designPatterns.behavioralPatterns.command.received.Stock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * received <- command <- broker
 * <p>
 * 遥控器 【绑定】-> 按钮 -> 【执行】功能
 */
@Slf4j
public class UseBroker {
	@Test
	void test() {
		Stock stock = new Stock();

		BuyOrder buyOrder = new BuyOrder(stock);
		SellOrder sellOrder = new SellOrder(stock);

		Broker broker = new Broker();
		broker.takeOrder(buyOrder);
		broker.takeOrder(sellOrder);

		broker.placeOrders();
	}
}
