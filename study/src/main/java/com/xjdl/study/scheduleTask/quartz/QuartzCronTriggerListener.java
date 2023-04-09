package com.xjdl.study.scheduleTask.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

@Slf4j
public class QuartzCronTriggerListener implements TriggerListener {
    @Override
    public String getName() {
        return "当前triggerListener的名 " + this.getClass().getName();
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {
        log.info(trigger.getKey().getName() + " 在job将要被执行了");
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        boolean choice = false;
        log.info(trigger.getKey().getName() + " 做出选择是否拒绝执行job，结果为" + (choice ? "是，禁止执行" : "否，立即执行"));
        return choice;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        log.info(trigger.getKey().getName() + " 错过触发时调用");
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {
        log.info(trigger.getKey().getName() + " 完成触发并且job执行完毕时调用");
    }
}
