package com.modules.system.mapper;

import com.common.mapper.BaseMapper;
import com.modules.system.entity.SysAttachmentInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统附件表
 * 
 * @author xmh
 * @email 
 * @date 2018-07-29 10:41:23
 */
@Mapper
public interface SysAttachmentInfoMapper extends BaseMapper<SysAttachmentInfo> {

    /**
     * 查询某个业务ID系统附件表信息
     * @param bizId
     */
    List<SysAttachmentInfo> getByBizId(String bizId);

    /**
     * 批量保存系统附件表
     * @param list
     */
    void batchInsert(List<SysAttachmentInfo> list);

    /**
     * 根据业务ID删除系统附件表
     * @param bizId
     */
    void deleteByBizId(String bizId);
}
