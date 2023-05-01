package com.xjdl.study.scheduleTask.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * Quartz框架入门使用
 */
@Slf4j
public class QuartzSimpleDemo1 {
	public static void main(String[] args) {
		try {
			// 创建调度器，参数存放于quartz.properties中，可以自定义初始化参数
			// 创建建调度器方式一
			StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
//            stdSchedulerFactory.initialize(new Properties(){{
//                put("key", "value");
//            }});
			// 或者直接调用静态方法即可获取默认的调度器
			Scheduler scheduler = stdSchedulerFactory.getScheduler();
			// 创建建调度器方式二 you must call createRemoteScheduler or createScheduler methods before calling getScheduler()
//            Scheduler scheduler = DirectSchedulerFactory.getInstance().getScheduler();

			// 创建jobDetail并与任务类绑定
			JobDetail jobDetail = JobBuilder.newJob(QuartzSimpleJobDemo1.class)
					.withIdentity("job1", "JobGroup1")
					// 添加数据到JobDataMap
					.usingJobData("message", "info")
					.build();

			// 获得任务实例的标识名和组名
			JobKey jobKey = jobDetail.getKey();
			log.info("任务实例的标识{}", jobKey.getName());
			log.info("任务实例的组名{}", jobKey.getGroup());

			// 创建触发器
			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity("trigger1", "triggerGroup1")
					// 添加数据到JobDataMap
					.usingJobData("msg", "warning")
					.withSchedule(
							SimpleScheduleBuilder
									.repeatSecondlyForever(1) // 多长时间秒执行一次
									.withRepeatCount(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW) // 重复执行多少次，启动就会执行一次，总次数最少一次
					)
					.startNow()
					.build();

			// JobDataMap便于job实例执行的时候使用数据
			JobDataMap jobDetailJobDataMap = jobDetail.getJobDataMap();
			jobDetailJobDataMap.put("k1", "v1");
			jobDetailJobDataMap.forEach((key, value) -> log.info(key + " = " + value));

			JobDataMap triggerJobDataMap = trigger.getJobDataMap();
			triggerJobDataMap.put("k2", "v2");
			triggerJobDataMap.forEach((key, value) -> log.info(key + " = " + value));

			// 整合任务
			Date scheduleJobStartTime = scheduler.scheduleJob(jobDetail, trigger);
			log.info("调度器开始运行时间 {}", scheduleJobStartTime);

			// 启动任务
			scheduler.start();
			// 挂起/暂停任务
//            scheduler.standby();
			// 关闭任务 默认值false，直接关闭调度器，true表示执行完当前任务再关闭
//            scheduler.shutdown(true);
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
		}
	}
}
