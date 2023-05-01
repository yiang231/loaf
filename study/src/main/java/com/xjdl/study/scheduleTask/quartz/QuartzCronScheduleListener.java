package com.xjdl.study.scheduleTask.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

@Slf4j
public class QuartzCronScheduleListener implements SchedulerListener {
	@Override
	public void jobScheduled(Trigger trigger) {
		log.info("job被装载时调用" + trigger.getKey().getName());
	}

	@Override
	public void jobUnscheduled(TriggerKey triggerKey) {
		log.info("job被卸载时调用" + triggerKey.getName());
	}

	@Override
	public void triggerFinalized(Trigger trigger) {
		log.info(trigger.getKey().getName() + "再也不会被触发时调用。如果执行的job未被持久化，则会从调度器中被删除");
	}

	@Override
	public void triggerPaused(TriggerKey triggerKey) {
		log.info("QuartzCronScheduleListener.triggerPaused");
	}

	@Override
	public void triggersPaused(String s) {
		log.info("QuartzCronScheduleListener.triggersPaused");
	}

	@Override
	public void triggerResumed(TriggerKey triggerKey) {
		log.info("QuartzCronScheduleListener.triggerResumed");
	}

	@Override
	public void triggersResumed(String s) {
		log.info("QuartzCronScheduleListener.triggersResumed");
	}

	@Override
	public void jobAdded(JobDetail jobDetail) {
		log.info("QuartzCronScheduleListener.jobAdded");
	}

	@Override
	public void jobDeleted(JobKey jobKey) {
		log.info(jobKey.getName() + "任务被删除时调用");
	}

	@Override
	public void jobPaused(JobKey jobKey) {
		log.info("QuartzCronScheduleListener.jobPaused");
	}

	@Override
	public void jobsPaused(String s) {
		log.info("QuartzCronScheduleListener.jobsPaused");
	}

	@Override
	public void jobResumed(JobKey jobKey) {
		log.info("QuartzCronScheduleListener.jobResumed");
	}

	@Override
	public void jobsResumed(String s) {
		log.info("QuartzCronScheduleListener.jobsResumed");
	}

	@Override
	public void schedulerError(String s, SchedulerException e) {
		log.info("QuartzCronScheduleListener.schedulerError");
	}

	@Override
	public void schedulerInStandbyMode() {
		log.info("该scheduler被挂起");
	}

	@Override
	public void schedulerStarted() {
		log.info("该scheduler已启动");
	}

	@Override
	public void schedulerStarting() {
		log.info("该scheduler启动中");
	}

	@Override
	public void schedulerShutdown() {
		log.info("该scheduler已关闭");
	}

	@Override
	public void schedulerShuttingdown() {
		log.info("该scheduler关闭中");
	}

	@Override
	public void schedulingDataCleared() {
		log.info("QuartzCronScheduleListener.schedulingDataCleared");
	}
}
