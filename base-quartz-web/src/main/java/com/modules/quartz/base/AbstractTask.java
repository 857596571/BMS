package com.modules.quartz.base;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.modules.quartz.entity.QuartzTask;
import com.modules.quartz.entity.QuartzTaskLog;
import com.modules.quartz.service.QuartzTaskLogService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 调度任务基类
 */
public abstract class AbstractTask implements Job {

    /**
     * 日志
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 任务执行日志
     */
    @Autowired
    protected QuartzTaskLogService quartzTaskLogService;

    /**
     * 执行方法
     * @param context
     * @param task
     */
    protected abstract void executeInternal(JobExecutionContext context, QuartzTask task);

    /**
     * cron表达式
     */
    protected String cronExpression;

    @Override
    public void execute(JobExecutionContext context) {
        QuartzTask task = (QuartzTask) context.getJobDetail().getJobDataMap().get("task");
        //构建日志基础信息
        QuartzTaskLog log = new QuartzTaskLog();
        log.setQuartzId(task.getId());
        log.setBeanId(task.getBeanId());
        log.setParams(task.getParams());

        logger.info("开始执行任务-"+task.getName());
        try {
            TimeInterval timer = DateUtil.timer();

            //执行
            executeInternal(context, task);

            //保存任务日志
            log.setState("SUCCESS");
            log.setTimes(timer.interval());
            quartzTaskLogService.save(log);
        } catch (Exception e) {
            //保存任务日志
            log.setState("ERROR");
            log.setTimes(0L);
            log.setError(e.getMessage());
            quartzTaskLogService.save(log);
            logger.error(e.getMessage(), e);
            logger.error("job execute failed!");
        }

        logger.info("结束执行任务-"+task.getName());
    }

    public String getCronExpression() {
        return cronExpression;
    }
}
