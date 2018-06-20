package com.modules.system.entity;

import com.common.api.DataEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户
 */
public class SysUser extends DataEntity {

    /**
     * 超级管理用户ID
     */
    public static final String ADMIN_USER_ID = "1";

    /**
     * 机构ID
     */
    private Integer orgId;
    /**
     * 角色id
     */
    private Integer roleId;
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
     * 是否可用
     */
    private Integer state;
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

    public SysUser(String id) {
        super(id);
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String getRemarks() {
        return remarks;
    }

    @Override
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public List<SysMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<SysMenu> menus) {
        this.menus = menus;
    }

    public List<SysOrg> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<SysOrg> orgs) {
        this.orgs = orgs;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }
}