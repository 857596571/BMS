package com.modules.system.security.model;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.util.StrUtil;
import com.common.security.model.AbstractAuthUser;
import com.modules.system.entity.SysMenu;
import com.modules.system.entity.SysOrg;
import com.modules.system.entity.SysRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Security User
 */
@Data
public class AuthUser extends AbstractAuthUser {

    public static Cache<String,String> LOGIN_SYSTEM = CacheUtil.newFIFOCache(100000);


    /**
     * 超级管理用户ID
     */
    public static final String ADMIN_USER_ID = "1";

    /**
     * id
     */
    private Long id;
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


    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }


    public boolean isAdmin() {
        return this.getId() != null ? this.getId().equals(ADMIN_USER_ID) : false;
    }
}
