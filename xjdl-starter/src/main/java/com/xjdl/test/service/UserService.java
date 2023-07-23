package com.xjdl.test.service;


import com.xjdl.beans.factory.annotation.Autowired;
import com.xjdl.stereotype.Component;

@Component("userService")
public class UserService implements BaseService {
	@Autowired
	private OrderService orderService;

	public void test() {
		System.out.println(orderService);
	}

}
