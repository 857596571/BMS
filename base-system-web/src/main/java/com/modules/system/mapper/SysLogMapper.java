package com.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.modules.system.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;
/**
 * 操作日志
 * 
 * @author henrycao
 * @email 631079326@qq.com
 * @date 2017-06-30 11:31:53
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {
	
}
