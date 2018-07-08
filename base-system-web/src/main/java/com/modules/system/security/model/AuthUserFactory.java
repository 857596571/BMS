package com.modules.system.security.model;


import cn.hutool.core.util.StrUtil;
import com.modules.system.entity.SysMenu;
import com.modules.system.entity.SysRole;
import com.modules.system.entity.SysUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Auth user factory.
 *
 * @author dcp
 */
public final class AuthUserFactory {

    private AuthUserFactory() {
    }

    /**
     * Create auth user.
     *
     * @param user the user
     * @return the auth user
     */
    public static AuthUser create(SysUser user) {
        AuthUser authUser = new AuthUser(user.getId());
        authUser.setOrgId(user.getOrgId());
        authUser.setLoginName(user.getLoginName());
        authUser.setName(user.getName());
        authUser.setEmail(user.getEmail());
        authUser.setPhone(user.getPhone());
        authUser.setMobile(user.getMobile());
        authUser.setPassword(user.getPassword());
        authUser.setState(user.getState());
        authUser.setMenus(user.getMenus());
        authUser.setOrgs(user.getOrgs());
        authUser.setRoles(user.getRoles());
        authUser.setAuthorities(mapToGrantedAuthorities(user.getRoles(), user.getMenus()));
        return authUser;
    }

    /**
     * 权限转换
     *
     * @param sysRoles 角色列表
     * @param sysMenus 菜单列表
     * @return 权限列表
     */
    private static List<SimpleGrantedAuthority> mapToGrantedAuthorities(List<SysRole> sysRoles, List<SysMenu> sysMenus) {

        List<SimpleGrantedAuthority> authorities =
            sysRoles.stream().filter(role -> role.getState().equals("ON"))
                .map(sysRole -> new SimpleGrantedAuthority(sysRole.getCode())).collect(Collectors.toList());

        // 添加基于Permission的权限信息
        sysMenus.stream().filter(menu -> StrUtil.isNotBlank(menu.getPermission())).forEach(menu -> {
            // 添加基于Permission的权限信息
            for (String permission : StrUtil.split(menu.getPermission(), ",")) {
                authorities.add(new SimpleGrantedAuthority(permission));
            }
        });

        return authorities;
    }

}
