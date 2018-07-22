package com.modules.quartz.service.impl;

import com.common.service.BaseServiceImpl;
import com.modules.config.QuartzManager;
import com.modules.quartz.entity.QuartzTask;
import com.modules.quartz.mapper.QuartzTaskMapper;
import com.modules.quartz.service.QuartzTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 任务调度资源
 *
 * @author xmh
 * @email 
 * @date 2018-07-22 00:03:20
 */
@Service
public class QuartzTaskServiceImpl extends BaseServiceImpl<QuartzTaskMapper, QuartzTask> implements QuartzTaskService {
    @Autowired
    private QuartzTaskMapper quartzTaskMapper;
    @Autowired
    private QuartzManager quartzManager;

    @Override
    public QuartzTask save(QuartzTask entity) {
        if (entity.getIsNewRecord()) {
            entity.preInsert();
            mapper.insert(entity);
            quartzManager.addTask(entity);
        } else {
            entity.preUpdate();
            mapper.update(entity);
            quartzManager.updateTaskCron(entity);
        }
        return entity;
    }

    @Override
    public void deleteById(String id) {
        QuartzTask task = get(id);
        super.deleteById(id);
        quartzManager.deleteJob(task);
    }

    @Override
    public void updateState(QuartzTask task) {
        quartzTaskMapper.updateState(task);
        QuartzTask quartzTask = get(task.getId());
        if(task.getState().equals("ON")) {
            quartzManager.resumeJob(quartzTask);
        } else {
            quartzManager.pauseJob(quartzTask);
        }
    }

    @Override
    public void runOne(QuartzTask task) {

    }
}
