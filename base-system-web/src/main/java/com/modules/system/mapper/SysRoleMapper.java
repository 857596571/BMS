package com.modules.system.mapper;


import com.common.mapper.BaseMapper;
import com.modules.system.entity.SysRole;
import com.modules.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 角色DAO接口
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 查询用户角色列表
     *
     * @param userId
     * @return
     */
    List<SysRole> findListByUserId(String userId);

    /**
     * 删除角色菜单
     *
     * @param role
     * @return
     */
    int deleteRoleMenu(SysRole role);

    /**
     * 插入角色菜单
     *
     * @param role
     * @return
     */
    int insertRoleMenu(SysRole role);

    /**
     * 删除角色机构
     * @param role
     * @return
     */
    int deleteRoleOrg(SysRole role);

    /**
     * 插入角色部门
     *
     * @param role
     * @return
     */
    int insertRoleOrg(SysRole role);

    /**
     * 删除角色用户
     * @param role
     */
    void deleteRoleUser(SysRole role);

    /**
     * 分配角色用户
     * @param role
     */
    void assignRole(SysRole role);

    /**
     * 获取角色用户列表
     * @param role
     * @return
     */
    List<SysUser> getRoleUsers(SysRole role);

    /**
     * 验证编码是否唯一
     * @param role
     * @return
     */
    int isCodeExists(SysRole role);

    /**
     * 更新角色状态
     * @param param
     */
    void updateRoleStates(Map<String,String> param);
}
