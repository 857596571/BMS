package com.modules.config;

import cn.hutool.core.collection.CollUtil;
import com.common.web.util.SpringContextHolder;
import com.modules.quartz.base.AbstractTask;
import com.modules.quartz.entity.QuartzTask;
import com.modules.quartz.service.QuartzTaskService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;

/**
 * 任务调度处理
 * @author xmh
 *
 */
@Configuration
@Scope("singleton")
public class QuartzManager {

    /**
     * 调度任务分组名称
     */
    private static final String JOB_DEFAULT_GROUP_NAME = "JOB_DEFAULT_GROUP_NAME";

    /**
     * 调度任务触发器分组名称
     */
    private static final String TRIGGER_DEFAULT_GROUP_NAME = "TRIGGER_DEFAULT_GROUP_NAME";

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 任务调度
     */
    @Autowired
    private Scheduler scheduler;

    /**
     * 任务调度资源服务
     */
    @Autowired
    private QuartzTaskService quartzTaskService;

    /**
     * 开始执行所有任务
     *
     * @throws SchedulerException
     */
    public void start() {
        QuartzTask queryTask = new QuartzTask();
        queryTask.setState("ON");
        //获取所有状态为启动（OFF）的任务调度数据
        List<QuartzTask> taskList = quartzTaskService.findList(queryTask);
        if(CollUtil.isNotEmpty(taskList)) {
            for (QuartzTask task : taskList) {
                addTask(task);
            }
        } else {
            logger.warn("无状态为启动（ON）状态的任务调度数据");
        }
    }

    /**
     * 添加一个任务
     * @param task
     */
    public void addTask(QuartzTask task) {
        try {
            // 通过JobBuilder构建JobDetail实例，JobDetail规定只能是实现Job接口的实例
            // JobDetail 是具体Job实例
            AbstractTask abstractTask = (AbstractTask) SpringContextHolder.getBean(task.getBeanId());
            //设置任务数据
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("task", task);
            JobDetail jobDetail = JobBuilder.newJob(abstractTask.getClass())
                    .withIdentity(task.getBeanId(), JOB_DEFAULT_GROUP_NAME)
                    .usingJobData(jobDataMap).build();
            // 基于表达式构建触发器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();
            // CronTrigger表达式触发器 继承于Trigger
            // TriggerBuilder 用于构建触发器实例
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(task.getBeanId(), TRIGGER_DEFAULT_GROUP_NAME)
                    .withSchedule(cronScheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
            scheduler.start();
            logger.info("=======================添加任务=======================");
            logger.info("调度任务名称：【"+ task.getName() +"】，" +
                    "运行类：【"+ abstractTask.getClass() +"】，" +
                    "运行表达式：【"+ task.getCronExpression() +"】，" +
                    "运行参数：【"+ task.getParams() +"】");
            logger.info("====================================================");
        } catch (Exception e) {
            logger.error("任务调度添加失败："+ e.getMessage(), e);
        }

    }

    /**
     * 更新某个任务的CRON表达式
     *
     * @param task
     * @return
     */
    public void updateTaskCron(QuartzTask task) {
        try {
            TriggerKey triggerKey = new TriggerKey(task.getBeanId(), TRIGGER_DEFAULT_GROUP_NAME);
            CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            String oldTime = cronTrigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(task.getCronExpression())) {
                CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression())
                        .withMisfireHandlingInstructionDoNothing();
                CronTrigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity(task.getBeanId(), TRIGGER_DEFAULT_GROUP_NAME)
                        .withSchedule(cronScheduleBuilder).build();
                scheduler.rescheduleJob(triggerKey, trigger);
                if(task.getState().equals("ON")) {
                    resumeJob(task);
                } else {
                    pauseJob(task);
                }
            }
            logger.info("=====================更新任务CRON=====================");
            logger.info("调度任务名称：【"+ task.getName() +"】，" +
                    "原运行表达式：【"+ oldTime +"】，" +
                    "新运行表达式：【"+ task.getCronExpression() +"】");
            logger.info("====================================================");
        } catch (Exception e) {
            logger.error("更新任务CRON失败："+ e.getMessage(), e);
        }
    }

    /**
     * 暂停所有任务
     */
    public void pauseAllTask() {
        try {
            scheduler.pauseAll();
            logger.info("=====================暂停所有任务=====================");
            logger.info("====================================================");
        } catch (Exception e) {
            logger.error("暂停所有任务失败："+ e.getMessage(), e);
        }
    }

    /**
     * 暂停某个任务
     *
     * @param task
     */
    public void pauseJob(QuartzTask task) {
        try {
            JobKey jobKey = new JobKey(task.getBeanId(), JOB_DEFAULT_GROUP_NAME);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail == null) {
                return;
            }
            scheduler.pauseJob(jobKey);
            logger.info("=======================暂停任务======================");
            logger.info("调度任务名称：【"+ task.getName() +"】，" +
                    "运行表达式：【"+ task.getCronExpression() +"】，" +
                    "运行参数：【"+ task.getParams() +"】");
            logger.info("====================================================");
        } catch (Exception e) {
            logger.error("暂停任务失败："+ e.getMessage(), e);
        }
    }

    /**
     * 恢复所有任务
     */
    public void resumeAllJob() {
        try {
            scheduler.resumeAll();
            logger.info("=====================恢复所有任务=====================");
            logger.info("====================================================");
        } catch (Exception e) {
            logger.error("恢复所有任务失败："+ e.getMessage(), e);
        }
    }

    /**
     * 恢复某个任务
     *
     * @param task
     */
    public void resumeJob(QuartzTask task) {
        try {
            JobKey jobKey = new JobKey(task.getBeanId(), JOB_DEFAULT_GROUP_NAME);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail == null) {
                return;
            }
            scheduler.resumeJob(jobKey);
            updateTaskCron(task);
            logger.info("=====================恢复某个任务======================");
            logger.info("调度任务名称：【"+ task.getName() +"】，" +
                    "运行表达式：【"+ task.getCronExpression() +"】，" +
                    "运行参数：【"+ task.getParams() +"】");
            logger.info("====================================================");
        } catch (Exception e) {
            logger.error("恢复某个任务失败："+ e.getMessage(), e);
        }
    }

    /**
     * 删除某个任务
     *
     * @param task
     * @exception SchedulerException
     */
    public void deleteJob(QuartzTask task) {
        try {
            JobKey jobKey = new JobKey(task.getBeanId(), JOB_DEFAULT_GROUP_NAME);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail == null) {
                return;
            }
            scheduler.deleteJob(jobKey);
            logger.info("=====================删除某个任务======================");
            logger.info("调度任务名称：【"+ task.getName() +"】，" +
                    "运行表达式：【"+ task.getCronExpression() +"】，" +
                    "运行参数：【"+ task.getParams() +"】");
            logger.info("====================================================");
        } catch (Exception e) {
            logger.error("删除某个任务失败："+ e.getMessage(), e);
        }
    }

}