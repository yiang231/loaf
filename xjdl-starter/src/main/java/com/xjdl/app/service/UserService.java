package com.xjdl.app.service;


import com.xjdl.framework.beans.factory.annotation.Autowired;
import com.xjdl.framework.stereotype.Component;

@Component("userService")
public class UserService implements BaseService {
    @Autowired
    private OrderService orderService;

    public void test() {
        System.out.println(orderService);
    }
}
