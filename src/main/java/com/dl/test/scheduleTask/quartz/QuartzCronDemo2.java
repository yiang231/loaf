package com.dl.test.scheduleTask.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.EverythingMatcher;

/**
 * CronSchedule任务创建
 * <p>
 * 秒 分 时 日 月 周 年
 * 除了最后一位年都是必填字段
 * `*` 代表每个，每个月，一周中的每一天
 * `?` 代表不确定值，在日或者周使用【互斥关系】，不需要和设置的这个字段有关系就用问号。
 * `-` 代表区间，小时设置为9-12，代表9,10,11,12这四个小时
 * `,` 代表多个值，周设置为1,2,6，代表周一，周二，周六这三天
 * `/` 代表值的增量，分钟设置3/20，代表从第三分钟开始，每隔二十分钟
 * `L` 在日或者是周中使用
 * 在日中  直接写表示当前最后一天
 * 在周中  直接写表示当前最后一周那几天。跟在值的后面，如6L，代表最后一个周五
 * `W` 在日中使用，代表距离某天最近的工作日
 * `LW` 在周中使用，代表本月的最后一个工作日
 * `#` 在周中使用，代表第几周的星期几，6#3代表第三周的周五（第六天）
 */
@Slf4j
public class QuartzCronDemo2 {
    public static void main(String[] args) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            JobDetail jobDetail = JobBuilder.newJob(QuartzCronJobDemo2.class)
                    .withIdentity("job2", "jobGroup2")
                    .build();

            Trigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger2", "triggerGroup2")
                    .withSchedule(
                            CronScheduleBuilder
                                    .cronSchedule("0/5 * * * * ?")
                    )
                    .startNow()
                    .build();

            scheduler.scheduleJob(jobDetail, cronTrigger);

            QuartzCronJobListener quartzCronJobListener = new QuartzCronJobListener();
            log.info(quartzCronJobListener.getName());

            QuartzCronTriggerListener quartzCronTriggerListener = new QuartzCronTriggerListener();
            log.info(quartzCronTriggerListener.getName());

            // 全局监听器，调度器内所有job被调度时会被监听
            scheduler.getListenerManager().addJobListener(quartzCronJobListener, EverythingMatcher.allJobs());
            scheduler.getListenerManager().addTriggerListener(quartzCronTriggerListener, EverythingMatcher.allTriggers());
            scheduler.getListenerManager().addSchedulerListener(new QuartzCronScheduleListener());
            // 局部监听器_1
//            scheduler.getListenerManager().addJobListener(quartzCronJobListener);
            // 局部监听器_2
//            scheduler.getListenerManager().addJobListener(quartzCronJobListener, KeyMatcher.keyEquals(JobKey.jobKey("job2","jobGroup2")));

            scheduler.start();
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }
}
