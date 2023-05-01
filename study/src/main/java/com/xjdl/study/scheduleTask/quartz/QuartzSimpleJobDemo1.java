package com.xjdl.study.scheduleTask.quartz;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import java.util.Date;

/**
 * job的有无状态
 * 默认无状态
 * <p>
 * 有状态表示job的执行期间有些状态数据存在JobDataMap中，可以发生改变。而默认的无状态job，每次调用时都会创建新的jobDataMap
 * 具体表现为count的值保持不变
 * 可以通过org.quartz.PersistJobDataAfterExecution注解改为有状态
 */
@Slf4j
@Data
@PersistJobDataAfterExecution
public class QuartzSimpleJobDemo1 implements Job {
	private String message;
	private String msg;
	private String k1;
	private String k2;
	private int count;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// 通过JobExecutionContext获取JobDetail
		JobDetail jobDetail = context.getJobDetail();
		JobKey jobKey = jobDetail.getKey();
		log.info("任务实例的标识 {}", jobKey.getName());
		log.info("任务实例的组名 {}", jobKey.getGroup());

		// 通过JobExecutionContext获取Trigger
		Trigger trigger = context.getTrigger();
		TriggerKey triggerKey = trigger.getKey();
		log.info("触发器名称 {}", triggerKey.getName());
		log.info("触发器组名 {}", triggerKey.getGroup());

		// JobDataMap便于job实例执行的时候使用数据
		jobDetail.getJobDataMap().forEach((key, value) -> log.info(key + " = " + value));
		trigger.getJobDataMap().forEach((key, value) -> log.info(key + " = " + value));

		log.info("job类的相关信息 {}", context.getClass().getName());

		log.info("QuartzDemo1 任务被执行 {}", new Date());

		log.info("本次任务的执行时间 {}", context.getFireTime());
		log.info("下次任务的执行时间 {}", context.getNextFireTime());

		// 从属性中获取数据
		log.info(message);
		log.info(msg);
		log.info(k1);
		log.info(k2);

		count++;
		jobDetail.getJobDataMap().put("count", count);
		log.info(String.valueOf(count));
	}
}
