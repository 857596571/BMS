package com.modules.system.mapper;


import com.common.mapper.BaseMapper;
import com.modules.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户DAO接口
 *
 * @author dcp
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据登录名称查询用户
     *
     * @param loginName 登录名
     * @return SysUser by login name
     */
    SysUser getByLoginName(String loginName);

    /**
     * 更新用户密码
     *
     * @param user the user
     * @return the int
     */
    int updatePasswordById(SysUser user);

    /**
     * 删除用户角色关联数据
     *
     * @param user the user
     * @return the int
     */
    int deleteUserRole(SysUser user);

    /**
     * 插入用户角色关联数据
     *
     * @param user the user
     * @return the int
     */
    int insertUserRole(SysUser user);

    /**
     * 获取指定角色的用户列表
     * @param roleId
     * @return
     */
    List<SysUser> getUsersByRoleId(String roleId);

    /**
     * 更新用户状态
     * @param param
     */
    void updateUserStates(Map<String,String> param);

    /**
     * 判断登录账号是否唯一
     * @param user
     * @return
     */
    int isExists(SysUser user);
}
