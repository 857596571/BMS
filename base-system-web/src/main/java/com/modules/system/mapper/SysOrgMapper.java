package com.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.modules.system.entity.SysOrg;

import java.util.Map;

/**
 * 机构管理
 */
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

    /**
     * 更新排序
     * @param sysOrg
     */
    void updateSort(SysOrg sysOrg);

    /**
     * 更新状态
     * @param param
     */
    void updateStates(Map<String,String> param);
}
