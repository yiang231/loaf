package com.dl.work.timerDemo;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Timer;

public class TimerListenerTest implements ServletContextListener {
    private Timer timer = new Timer();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("定时器开启");
        timer.schedule(new TestTask(), 0, 10000);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        timer.cancel();
        System.out.println("定时器关闭");
    }
}
