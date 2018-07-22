package com.modules.quartz.mapper;

import com.common.mapper.BaseMapper;
import com.modules.quartz.entity.QuartzTask;
import org.apache.ibatis.annotations.Mapper;

/**
 * 任务调度资源
 * 
 * @author xmh
 * @email 
 * @date 2018-07-22 00:03:20
 */
@Mapper
public interface QuartzTaskMapper extends BaseMapper<QuartzTask> {

    /**
     * 更新定时任务状态
     * @param task
     */
    void updateState(QuartzTask task);
}
