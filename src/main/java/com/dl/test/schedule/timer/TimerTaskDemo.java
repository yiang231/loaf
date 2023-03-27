package com.dl.test.schedule.timer;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 所有的Timer都是单线程的，打印日志看似按时执行，实则先打印，后执行
 */
public class TimerTaskDemo {
    public static void main(String[] args) {
//        demo1();
//        demo2();
//        demo3();
//        demo4();
//        demo5();
        demo6();
    }

    /**
     * 指定开始的日期和时间，以指定周期运存任务，开啥时间后的任务会全部执行
     */
    private static void demo6() {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());

//        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 45);
        calendar.set(Calendar.SECOND, 14);
        System.out.println(calendar.getTime());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(new Date(scheduledExecutionTime()));
            }
        }, calendar.getTime(), 1000 * 60 * 60 * 24);
    }

    /**
     * 延迟一段时间以固定频率执行任务
     */
    private static void demo5() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(new Date(scheduledExecutionTime()));
            }
        }, 2000, 2000);
    }

    /**
     * 延迟一段时间，按照间隔周期重复执行任务
     */
    private static void demo4() {
        System.out.println(new Date());
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(new Date(scheduledExecutionTime()));
            }
        }, 5000, 2000);
    }

    /**
     * 延迟一段时间只执行一次
     */
    private static void demo3() {
        System.out.println(new Date());
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(new Date(scheduledExecutionTime()));
            }
        }, 2000);
    }

    /**
     * 指定开始时间和间隔时间周期重复执行
     */
    private static void demo2() {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());

        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 1);
        System.out.println(calendar.getTime());

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(new Date(scheduledExecutionTime()));
            }
        }, calendar.getTime(), 2000);
    }

    /**
     * 指定时间执行一次
     * 定时器的取消
     */
    private static void demo1() {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());

        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 5);
        System.out.println(calendar.getTime());

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println(new Date(scheduledExecutionTime())); // 这个方法打印的是任务应该执行的时间
            }
        };
        timer.schedule(timerTask, calendar.getTime());

//        timerTask.cancel(); // 取消任务
//        timer.purge(); // 从定时器的队列任务中，移除所有已取消的任务
//        timer.cancel(); // 取消所有的任务，终止定时器
    }
}
