package com.modules.system.mapper;


import com.common.mapper.BaseMapper;
import com.modules.system.entity.SysDict;
import com.modules.system.entity.SysMenu;
import com.modules.system.entity.SysOrg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 菜单DAO接口
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户查询菜单
     * @param userId
     * @return
     */
    List<SysMenu> findListByUserId(String userId);

    /**
     * 查询左右值
     * @param menu
     * @return
     */
    Integer getLRNum(SysMenu menu);

    /**
     * 更新左值
     * @param menu
     */
    void updateLNum(SysMenu menu);

    /**
     * 更新右值
     * @param menu
     */
    void updateRNum(SysMenu menu);

    /**
     * 更新排序
     * @param sysMenu
     */
    void updateSort(SysMenu sysMenu);

    /**
     * 更新状态
     * @param param
     */
    void updateStates(Map<String,String> param);
}
