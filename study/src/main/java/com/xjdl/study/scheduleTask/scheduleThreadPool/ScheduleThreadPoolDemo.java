package com.xjdl.study.scheduleTask.scheduleThreadPool;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 使用scheduleThreadPool实现定时任务
 */
@Slf4j
public class ScheduleThreadPoolDemo {
	public static void main(String[] args) {
		ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1);

//        scheduleAtFixedRate(scheduled, 1000 * 60 * 60 * 24, "10:16:00");
//        scheduleWithFixedDelay(scheduled);
		scheduleWithCallable(scheduled);
	}

	/**
	 * scheduleWithCallable 有返回值
	 * 延迟一段时间后执行任务
	 *
	 * @param scheduled 调度线程池
	 */
	private static void scheduleWithCallable(ScheduledExecutorService scheduled) {
		scheduled.schedule((Callable<String>) () -> {
			log.info("scheduleWithCallable执行任务");
			return null;
		}, 5L, TimeUnit.SECONDS);
	}

	/**
	 * scheduleWithFixedDelay
	 * 指定频率间隔执行任务
	 * <p>
	 * 如果执行任务花费的时间大于period，则本次任务执行后，延迟delay时间后执行下次的任务
	 *
	 * @param scheduled 调度线程池
	 */
	private static void scheduleWithFixedDelay(ScheduledExecutorService scheduled) {
		scheduled.scheduleWithFixedDelay(() -> log.info("scheduleWithFixedDelay执行任务"), 0L, 5L, TimeUnit.SECONDS);
	}

	/**
	 * scheduleAtFixedRate
	 * 指定频率周期执行任务
	 * <p>
	 * 如果执行任务花费的时间大于period，则本次任务执行后，立即执行下次的任务
	 *
	 * @param scheduled 调度线程池
	 * @param oneDay    执行周期
	 * @param dailyTime 第一次执行时间
	 */
	private static void scheduleAtFixedRate(ScheduledExecutorService scheduled, long oneDay, String dailyTime) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");

			Date currentTime = dateFormat.parse(dayFormat.format(new Date()) + " " + dailyTime);

			long initialDelay = currentTime.getTime() - System.currentTimeMillis();
			initialDelay = initialDelay > 0 ? initialDelay : initialDelay + oneDay;
			scheduled.scheduleAtFixedRate(() -> log.info("{}", new Date()), initialDelay, 5L, TimeUnit.SECONDS);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
