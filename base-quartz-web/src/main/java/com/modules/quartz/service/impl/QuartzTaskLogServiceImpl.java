package com.modules.quartz.service.impl;

import com.common.service.BaseServiceImpl;
import com.modules.quartz.entity.QuartzTaskLog;
import com.modules.quartz.mapper.QuartzTaskLogMapper;
import com.modules.quartz.service.QuartzTaskLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 任务调度运行日志
 *
 * @author xmh
 * @email 
 * @date 2018-07-22 00:03:21
 */
@Service
public class QuartzTaskLogServiceImpl extends BaseServiceImpl<QuartzTaskLogMapper, QuartzTaskLog> implements QuartzTaskLogService {
    @Autowired
    private QuartzTaskLogMapper quartzTaskLogMapper;

}
