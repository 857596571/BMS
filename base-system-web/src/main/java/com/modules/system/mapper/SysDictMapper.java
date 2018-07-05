package com.modules.system.mapper;

import com.common.mapper.BaseMapper;
import com.modules.system.entity.SysDict;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 字典管理
 */
@Mapper
public interface SysDictMapper extends BaseMapper<SysDict> {

    /**
     * 判断字典编码是否唯一
     * @param sysDict
     * @return
     */
    int isCodeExists(SysDict sysDict);

    /**
     * 更新字典状态
     * @param param
     */
    void updateStates(Map<String, String> param);

    /**
     * 查询左右值
     * @param sysDict
     * @return
     */
    int getLRNum(SysDict sysDict);

    /**
     * 更新左值
     * @param sysDict
     */
    void updateLNum(SysDict sysDict);

    /**
     * 更新右值
     * @param sysDict
     */
    void updateRNum(SysDict sysDict);

    /**
     * 根据父编码查询Map结构的一级子字典，map key为code
     * @param codes
     */
    @MapKey("code")
    Map<String, SysDict> findMapByParentCodes(String[] codes);

}
