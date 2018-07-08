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

    public SysUser(String id) {
        super(id);
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
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

    /**
     * Getter method for property <tt>stateDesc</tt>.
     *
     * @return property value of stateDesc
     */
    public String getStateDesc() {
        return stateDesc;
    }

    /**
     * Setter method for property <tt>stateDesc</tt>.
     *
     * @param stateDesc value to be assigned to property stateDesc
     */
    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }
}