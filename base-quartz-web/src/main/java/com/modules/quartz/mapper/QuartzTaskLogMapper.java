package com.modules.quartz.mapper;

import com.common.mapper.BaseMapper;
import com.modules.quartz.entity.QuartzTaskLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 任务调度运行日志
 * 
 * @author xmh
 * @email 
 * @date 2018-07-22 00:03:21
 */
@Mapper
public interface QuartzTaskLogMapper extends BaseMapper<QuartzTaskLog> {

}
