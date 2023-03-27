package com.dl.test.schedule.scheduleThreadPool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 使用schedule线程池，开启定时任务
 */
public class ScheduleThreadPoolDemo {
    public static void main(String[] args) {
        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1);

        long oneDay = 1000 * 60 * 60 * 24;
        String dailyTime = "10:53:00";
        schedule(scheduled, oneDay, dailyTime);
    }

    /**
     * 开启定时任务
     *
     * @param scheduled 调度线程池
     * @param oneDay    执行周期
     * @param dailyTime 第一次执行时间
     */
    private static void schedule(ScheduledExecutorService scheduled, long oneDay, String dailyTime) {
        long initialDelay = getTimeMills(dailyTime) - System.currentTimeMillis();
        initialDelay = initialDelay > 0 ? initialDelay : initialDelay + oneDay;

        scheduled.scheduleAtFixedRate(() -> System.out.println(new Date()), initialDelay, 5000, TimeUnit.MILLISECONDS);
    }

    /**
     * 得到执行的开始时间
     * 需要指定日期的话使用Calendar方便
     *
     * @param time 开始时间
     */
    private static long getTimeMills(String time) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date currentTime = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return currentTime.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
