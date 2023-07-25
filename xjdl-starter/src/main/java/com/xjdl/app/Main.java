package com.xjdl.app;

import com.xjdl.app.config.AppConfig;
import com.xjdl.app.service.BaseService;
import com.xjdl.framework.context.XjdlApplicationContext;


public class Main {
    public static void main(String[] args) {
        XjdlApplicationContext applicationContext = new XjdlApplicationContext(AppConfig.class);

        BaseService userService = (BaseService) applicationContext.getBean("userService");
        userService.test();
    }
}
