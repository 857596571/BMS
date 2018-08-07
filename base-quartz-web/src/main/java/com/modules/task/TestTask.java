package com.modules.task;

import cn.hutool.json.JSONUtil;
import com.modules.quartz.base.AbstractTask;
import com.modules.quartz.entity.QuartzTask;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 测试任务
 */
@Component("testTask")
public class TestTask extends AbstractTask {


    @Override
    protected void executeInternal(JobExecutionContext context, QuartzTask task) {
        logger.info("task="+JSONUtil.toJsonStr(task));
        logger.info("test task start execute.");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            logger.info("test task execute interrupted.");
        }
        logger.info("test task execute end.");
    }
}
