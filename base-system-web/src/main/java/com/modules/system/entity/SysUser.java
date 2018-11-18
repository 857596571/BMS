package com.modules.system.entity;

import com.common.api.DataEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SysUser extends DataEntity {

    /**
     * 超级管理用户ID
     */
    public static final String ADMIN_USER_ID = "1";

    /**
     * 机构ID
     */
    private String orgId;
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 角色ids
     */
    private String roleIds;
    /**
     * 机构名称
     */
    private String orgName;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 密码
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    /**
     * 姓名
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 类型描述
     */
    private String typeDesc;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 状态
     */
    private String state;
    /**
     * 状态
     */
    private String stateDesc;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 最后一次重置密码的时间
     */
    private Date lastPasswordResetDate;
    /**
     * 角色列表
     */
    private List<SysRole> roles = new ArrayList<>();
    /**
     * 菜单列表
     */
    private List<SysMenu> menus = new ArrayList<>();
    /**
     * 机构列表
     */
    private List<SysOrg> orgs = new ArrayList<>();

    public SysUser() {}

    public SysUser(Long id) {
        super(id);
    }

}