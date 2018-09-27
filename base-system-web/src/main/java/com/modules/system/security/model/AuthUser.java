package com.modules.system.security.model;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.util.StrUtil;
import com.common.security.model.AbstractAuthUser;
import com.modules.system.entity.SysMenu;
import com.modules.system.entity.SysOrg;
import com.modules.system.entity.SysRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Security User
 */
public class AuthUser extends AbstractAuthUser {

    public static Cache<String,String> LOGIN_SYSTEM = CacheUtil.newFIFOCache(100000);


    /**
     * 超级管理用户ID
     */
    public static final String ADMIN_USER_ID = "1";

    /**
     * id
     */
    private String id;
    /**
     * 机构ID
     */
    private String orgId;
    /**
     * 机构名称
     */
    private String orgName;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 姓名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
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
     * 类型
     */
    private String type;
    /**
     * 权限
     */
    private Collection<SimpleGrantedAuthority> authorities;
    /**
     * 是否可用
     */
    private String state;
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

    public AuthUser(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginName;
    }

    @Override
    public boolean isEnabled() {
        return state.equals("ON");
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAdmin() {
        return StrUtil.isNotEmpty(this.getId()) ? this.getId().equals(ADMIN_USER_ID) : false;
    }
}
