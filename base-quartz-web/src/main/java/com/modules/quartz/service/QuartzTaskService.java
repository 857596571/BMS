package com.modules.quartz.service;

import com.common.service.BaseService;
import com.modules.quartz.entity.QuartzTask;

/**
 * 任务调度资源
 *
 * @author xmh
 * @email 
 * @date 2018-07-22 00:03:20
 */
public interface QuartzTaskService extends BaseService<QuartzTask> {

    /**
     * 更新定时任务状态
     * @param task
     */
    void updateState(QuartzTask task);

    /**
     * 立即运行一次调度任务
     * @param task
     */
    void runOne(QuartzTask task);
}
