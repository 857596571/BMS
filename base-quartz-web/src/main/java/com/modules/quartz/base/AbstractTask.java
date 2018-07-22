package com.modules.quartz.base;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 调度任务基类
 */
public abstract class AbstractTask implements Job {

    /**
     * 日志
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 执行方法
     * @param context
     */
    protected abstract void executeInternal(JobExecutionContext context);

    /**
     * cron表达式
     */
    protected String cronExpression;

    @Override
    public void execute(JobExecutionContext context) {
        try {
            executeInternal(context);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("job execute failed!");
        }
    }

    public String getCronExpression() {
        return cronExpression;
    }
}
