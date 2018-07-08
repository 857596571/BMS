package com.modules.system.service;


import com.common.api.Paging;
import com.github.pagehelper.PageInfo;
import com.modules.system.entity.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 */
public interface SystemService {

    /* 登录开始 */

    /**
     * 根据登录名获取用户
     * @param loginName 登录名
     * @return
     */
    SysUser getUserByLoginName(String loginName);

    /* 登录结束 */

    /* 字典开始 */

    /**
     * 获取制定字典编码下的所有子字典
     * @param code
     * @return
     */
    List<SysDict> getListByParentCode(String code);

    /**
     *
     * @param sysDict
     * @return
     */
    List<SysDict> findDictList(SysDict sysDict);

    /**
     * 判断字典编码是否唯一
     * @param sysDict
     * @return
     */
    Boolean isDictCodeExists(SysDict sysDict);

    /**
     * 保存字典
     * @param sysDict
     * @return
     */
    SysDict saveDict(SysDict sysDict);

    /**
     * 更新字典排序
     * @param list
     */
    void updateDictSorts(List<SysDict> list);

    /**
     * 更新字典状态
     * @param param
     */
    void updateDictStates(Map<String, String> param);

    /**
     * 删除字典
     * @param sysDict
     */
    void deleteDict(SysDict sysDict);

    /* 字典结束 */

    /* 菜单开始 */

    /**
     * 查询用户菜单列表
     * @return
     */
    List<SysMenu> findMenuList();

    /**
     * 删除菜单
     * @param menu
     */
    void deleteMenu(SysMenu menu);

    /**
     * 保存菜单
     * @param menu 菜单
     * @return
     */
    SysMenu saveMenu(SysMenu menu);

    /**
     * 更新菜单排序
     * @param list
     */
    void updateMenuSorts(List<SysMenu> list);

    /**
     * 更新菜单状态
     * @param param
     */
    void updateMenuStates(Map<String, String> param);

    /* 菜单结束 */

    /* 机构开始 */

    /**
     * 查询机构列表
     * @param org
     * @return
     */
    List<SysOrg> findOrgList(SysOrg org);

    /**
     * 验证机构编码是否唯一
     * @param org
     * @return
     */
    Boolean isOrgCodeExists(SysOrg org);

    /**
     * 保存机构
     * @param org
     * @return
     */
    SysOrg saveOrg(SysOrg org);

    /**
     * 更新机构排序
     * @param list
     * @return
     */
    void updateOrgSorts(List<SysOrg> list);

    /**
     * 更新机构状态
     * @param param
     */
    void updateOrgStates(Map<String, String> param);

    /**
     * 删除机构
     * @param sysOrg
     */
    void deleteOrg(SysOrg sysOrg);

    /* 机构结束 */

    /* 角色开始 */

    /**
     * 查询角色列表
     * @param role 角色
     * @return 角色 page info
     */
    List<SysRole> findRoleList(SysRole role);

    /**
     * 保存角色
     * @param role 角色
     * @return the sys role
     */
    SysRole saveRole(SysRole role);

    /**
     * 更新角色状态
     * @param param
     */
    void updateRoleStates(Map<String, String> param);

    /**
     * 保存角色
     * @param role 角色
     * @return the sys role
     */
    SysRole saveRoleMenuAuth(SysRole role);

    /**
     * 删除角色
     * @param roleId 角色ID
     */
    void deleteRoleById(String roleId);

    /**
     * 删除指定角色下的指定用户
     * @param role
     */
    void deleteRoleUser(SysRole role);

    /**
     * 分配角色用户
     * @param role
     */
    void assignRole(SysRole role);

    /**
     * 验证角色编码是否唯一
     * @param role
     * @return
     */
    Boolean isRoleCodeExists(SysRole role);

    /* 角色结束 */

    /* 用户开始 */

    /**
     * 查询用户列表
     * @param page 分页信息
     * @param user 用户
     * @return 分页数据 page info
     */
    PageInfo<SysUser> findUserPage(Paging page, SysUser user);

    /**
     * 获取指定角色的用户列表
     * @param roleId
     * @return
     */
    List<SysUser> getUsersByRoleId(String roleId);

    /**
     * 保存用户
     *
     * @param user 用户
     * @return the sys user
     */
    SysUser saveUser(SysUser user);

    /**
     * 更新用户状态
     * @param param
     */
    void updateUserStates(Map<String, String> param);

    /**
     * 删除用户
     *
     * @param userId 用户ID
     */
    void deleteUserById(String userId);

    /**
     * 修改密码
     *  @param userId      用户ID
     * @param newPassword 密码
     * @param lastPasswordResetDate
     */
    void updateUserPasswordById(String userId, String newPassword, Date lastPasswordResetDate);

    /**
     * 获取指定用户的信息
     * @param id
     * @return
     */
    SysUser getUserById(String id);

    /**
     * 判断登录账号是否唯一
     * @param user
     * @return
     */
    Boolean isUserExists(SysUser user);

    /* 用户结束 */


}
