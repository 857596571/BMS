package com.modules.system.mapper;

import com.common.mapper.BaseMapper;
import com.modules.system.entity.SysOrg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 机构管理
 */
@Mapper
public interface SysOrgMapper extends BaseMapper<SysOrg> {

    /**
     * 机构编码是否唯一
     * @param sysOrg
     * @return
     */
    int isCodeExists(SysOrg sysOrg);

    /**
     * 查询左右值
     * @param org
     * @return
     */
    Integer getLRNum(SysOrg org);

    /**
     * 更新左值
     * @param org
     */
    void updateLNum(SysOrg org);

    /**
     * 更新右值
     * @param org
     */
    void updateRNum(SysOrg org);
}
