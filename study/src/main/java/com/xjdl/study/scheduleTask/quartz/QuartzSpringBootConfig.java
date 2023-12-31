package com.xjdl.study.scheduleTask.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzSpringBootConfig {
	@Bean
	public JobDetail jobDetail() {
		//指定任务描述具体的实现类
		return JobBuilder.newJob(QuartzSpringBootJobDemo3.class)
				// 指定任务的名称
				.withIdentity("dongAoJob")
				// 任务描述
				.withDescription("任务描述：用于输出冬奥欢迎语")
				// 每次任务执行后进行存储
				.storeDurably()
				.build();
	}

	@Bean
	public Trigger trigger() {
		//创建触发器
		return TriggerBuilder.newTrigger()
				// 绑定工作任务
				.forJob(jobDetail())
				// 每隔 5 秒执行一次 job
				.withSchedule(SimpleScheduleBuilder.repeatHourlyForever(1))
				.build();
	}
}
