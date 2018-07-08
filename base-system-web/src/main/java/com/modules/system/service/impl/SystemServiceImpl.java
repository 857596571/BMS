package com.modules.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.common.api.DataEntity;
import com.common.api.Paging;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.modules.system.entity.*;
import com.modules.system.mapper.*;
import com.modules.system.service.SystemService;
import com.modules.system.utils.DictUtils;
import com.modules.system.utils.MenuUtils;
import com.modules.system.utils.OrgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 */
@Service
public class SystemServiceImpl implements SystemService {

    /**
     * 系统用户Mapper
     */
    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * 系统角色Mapper
     */
    @Autowired
    private SysRoleMapper sysRoleMapper;
    /**
     * 系统菜单Mapper
     */
    @Autowired
    private SysMenuMapper sysMenuMapper;
    /**
     * 系统机构Mapper
     */
    @Autowired
    private SysOrgMapper sysOrgMapper;
    /**
     * 系统字典Mapper
     */
    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public SysUser getUserByLoginName(String loginName) {
        SysUser user = sysUserMapper.getByLoginName(loginName);
        if (user == null) {
            return null;
        }

        String userId = user.getId();
        List<SysRole> roleList = sysRoleMapper.findListByUserId(userId);
        if(CollUtil.isEmpty(roleList)) {
            throw new UsernameNotFoundException("1007");
        }
        user.setRoles(roleList);

        List<SysMenu> menuList = getMenuListByUserId(userId);
        if(CollUtil.isEmpty(menuList)) {
            return null;
        }
        user.setMenus(MenuUtils.makeTree(menuList, true));
        return user;
    }

    @Override
    public List<SysDict> getListByParentCode(String code) {
        Map<String, Object> query = new HashMap<>(1);
        query.put("code", code);
        return sysDictMapper.findList(query);
    }

    public Map<String, SysDict> getMapByParentCode(String parentCode) {
        Map<String, SysDict> map = new HashMap<>();
        List<SysDict> list = getListByParentCode(parentCode);
        for (SysDict sysDict : list) {
            map.put(sysDict.getCode(), sysDict);
        }
        return map;
    }

    @Override
    public List<SysDict> findDictList(SysDict sysDict) {
        List<SysDict> list = sysDictMapper.findList(sysDict);
        if(CollUtil.isNotEmpty(list)) {
            Map<String, SysDict> dictMap = sysDictMapper.findMapByParentCodes(new String[]{"STATE", "LEVEL"});
            for (SysDict dict : list) {
                if(dictMap.get(dict.getLevel()) != null) {
                    dict.setLevelDesc(dictMap.get(dict.getLevel()).getLabel());
                }
                if(dictMap.get(dict.getState()) != null) {
                    dict.setStateDesc(dictMap.get(dict.getState()).getLabel());
                }
            }
        }
        return DictUtils.makeTree(list);
    }

    @Override
    public Boolean isDictCodeExists(SysDict sysDict) {
        return sysDictMapper.isCodeExists(sysDict) > 0;
    }

    @Override
    public SysDict saveDict(SysDict sysDict) {
        if(sysDict.getIsNewRecord()) {
            Integer rightNum = sysDictMapper.getLRNum(sysDict);
            rightNum = rightNum != null ? rightNum : 0;
            sysDict.setLeftNum(rightNum);
            sysDict.setRightNum(rightNum + 1);
            if(rightNum != 0 && sysDict.getParentId() != 0) {
                SysDict param = new SysDict();
                param.setRightNum(rightNum);
                param.setDelFlag("0");
                //如果不是根节点，才更新左右值
                sysDictMapper.updateLNum(param);
                sysDictMapper.updateRNum(param);
            }
            sysDictMapper.insert(sysDict);
        } else {
            sysDictMapper.update(sysDict);
        }
        return sysDict;
    }

    @Override
    public void updateDictSorts(List<SysDict> list) {
        for (SysDict sysDict : list) {
            sysDictMapper.updateSort(sysDict);
        }
    }

    @Override
    public void updateDictStates(Map<String, String> param) {
        sysDictMapper.updateStates(param);
    }

    @Override
    public void deleteDict(SysDict sysDict) {
        sysDictMapper.delete(sysDict);
        //计算节点数量
        sysDict.setTreeNodeNum(sysDict.getRightNum() - sysDict.getLeftNum() + 1);
        sysDict.setDelFlag(DataEntity.DEL_FLAG_DELETE);
        sysDictMapper.updateLNum(sysDict);
        sysDictMapper.updateRNum(sysDict);
    }

    /**
     * 获得用户菜单列表，超级管理员可以查看所有菜单
     * @param userId 用户ID
     * @return 菜单列表
     */
    private List<SysMenu> getMenuListByUserId(String userId) {
        List<SysMenu> menuList;
        //超级管理员
        if (SysUser.ADMIN_USER_ID.equals(userId) || StrUtil.isEmpty(userId)) {
            menuList = sysMenuMapper.findList();
        } else {
            menuList = sysMenuMapper.findListByUserId(userId);
        }
        return menuList;
    }

    @Override
    public List<SysMenu> findMenuList() {
        List<SysMenu> list = sysMenuMapper.findList();
        //按父子顺序排列菜单列表
        if(CollUtil.isNotEmpty(list)) {
            Map<String, SysDict> dictMap = sysDictMapper.findMapByParentCodes(new String[]{"STATE", "MENU_LEVEL"});
            for (SysMenu menu : list) {
                if(dictMap.get(menu.getLevel()) != null) {
                    menu.setLevelDesc(dictMap.get(menu.getLevel()).getLabel());
                }
                if(dictMap.get(menu.getState()) != null) {
                    menu.setStateDesc(dictMap.get(menu.getState()).getLabel());
                }
            }
        }
        return MenuUtils.makeTree(list, false);
    }

    @Override
    public SysMenu saveMenu(SysMenu menu) {
        if(menu.getIsNewRecord()) {
            Integer rightNum = sysMenuMapper.getLRNum(menu);
            rightNum = rightNum != null ? rightNum : 0;
            menu.setLeftNum(rightNum);
            menu.setRightNum(rightNum + 1);
            if(rightNum != 0 && menu.getParentId() != 0) {
                //如果不是根节点，才更新左右值
                SysMenu param = new SysMenu();
                param.setRightNum(rightNum);
                param.setDelFlag("0");
                sysMenuMapper.updateLNum(param);
                sysMenuMapper.updateRNum(param);
            }
            sysMenuMapper.insert(menu);
        } else {
            sysMenuMapper.update(menu);
        }
        return menu;
    }

    @Override
    public void updateMenuSorts(List<SysMenu> list) {
        for (SysMenu sysMenu : list) {
            sysMenuMapper.updateSort(sysMenu);
        }
    }

    @Override
    public void updateMenuStates(Map<String, String> param) {
        sysMenuMapper.updateStates(param);
    }

    @Override
    public void deleteMenu(SysMenu menu) {
        sysMenuMapper.delete(menu);
        //计算节点数量
        menu.setTreeNodeNum(menu.getRightNum() - menu.getLeftNum() + 1);
        menu.setDelFlag(DataEntity.DEL_FLAG_DELETE);
        sysMenuMapper.updateLNum(menu);
        sysMenuMapper.updateRNum(menu);
    }

    @Override
    public List<SysOrg> findOrgList(SysOrg org) {
        List<SysOrg> list = sysOrgMapper.findList(org);
        if(CollUtil.isNotEmpty(list)) {
            Map<String, SysDict> dictMap = sysDictMapper.findMapByParentCodes(new String[]{"ORG_TYPE", "ORG_LEVEL"});
            for (SysOrg sysOrg : list) {
                if(dictMap.get(sysOrg.getLevel()) != null) {
                    sysOrg.setLevelDesc(dictMap.get(sysOrg.getLevel()).getLabel());
                }
                if(dictMap.get(sysOrg.getType()) != null) {
                    sysOrg.setTypeDesc(dictMap.get(sysOrg.getType()).getLabel());
                }
            }
        }
        return OrgUtils.makeTree(list);
    }

    @Override
    public Boolean isOrgCodeExists(SysOrg org) {
        return sysOrgMapper.isCodeExists(org) > 0;
    }

    @Override
    public SysOrg saveOrg(SysOrg org) {
        if(org.getIsNewRecord()) {
            Integer rightNum = sysOrgMapper.getLRNum(org);
            rightNum = rightNum != null ? rightNum : 0;
            org.setLeftNum(rightNum);
            org.setRightNum(rightNum + 1);
            if(rightNum != 0 && org.getParentId() != 0) {
                //如果不是根节点，才更新左右值
                SysOrg param = new SysOrg();
                param.setRightNum(rightNum);
                param.setDelFlag("0");
                sysOrgMapper.updateLNum(param);
                sysOrgMapper.updateRNum(param);
            }
            sysOrgMapper.insert(org);
        } else {
            sysOrgMapper.update(org);
        }
        return org;
    }

    @Override
    public void updateOrgSorts(List<SysOrg> list) {
        if(CollUtil.isNotEmpty(list)) {
            for (SysOrg sysOrg : list) {
                sysOrgMapper.updateSort(sysOrg);
            }
        }
    }

    @Override
    public void updateOrgStates(Map<String, String> param) {
        sysOrgMapper.updateStates(param);
    }

    @Override
    public void deleteOrg(SysOrg org) {
        sysOrgMapper.delete(org);
        //计算节点数量
        org.setTreeNodeNum(org.getRightNum() - org.getLeftNum() + 1);
        org.setDelFlag(DataEntity.DEL_FLAG_DELETE);
        sysOrgMapper.updateLNum(org);
        sysOrgMapper.updateRNum(org);
    }

    @Override
    public List<SysRole> findRoleList(SysRole role) {
        List<SysRole> list = sysRoleMapper.findList(role);
        if(CollUtil.isNotEmpty(list)) {
            Map<String, SysDict> dictMap = sysDictMapper.findMapByParentCodes(new String[]{"STATE", "LEVEL", "DATA_SCOPE"});
            for (SysRole sysRole : list) {
                if(dictMap.get(sysRole.getLevel()) != null) {
                    sysRole.setLevelDesc(dictMap.get(sysRole.getLevel()).getLabel());
                }
                if(dictMap.get(sysRole.getState()) != null) {
                    sysRole.setStateDesc(dictMap.get(sysRole.getState()).getLabel());
                }
                if(dictMap.get(sysRole.getDataScope()) != null) {
                    sysRole.setDataScopeDesc(dictMap.get(sysRole.getDataScope()).getLabel());
                }
            }
        }
        return list;
    }

    @Override
    public SysRole saveRole(SysRole role) {
        if (role.getIsNewRecord()) {
            role.preInsert();
            sysRoleMapper.insert(role);
        } else {
            // 更新角色数据
            role.preUpdate();
            sysRoleMapper.update(role);
        }
        sysRoleMapper.deleteRoleOrg(role);
        if(StrUtil.equals(role.getDataScope(), "SCOPE_DETAIL")) {
            if(CollUtil.isNotEmpty(role.getOrgs())) {
                //更新角色数据权限
                sysRoleMapper.insertRoleOrg(role);
            }
        }
        return role;
    }

    @Override
    public void updateRoleStates(Map<String, String> param) {
        sysRoleMapper.updateRoleStates(param);
    }

    @Override
    public SysRole saveRoleMenuAuth(SysRole role) {
        sysRoleMapper.deleteRoleMenu(role);
        sysRoleMapper.insertRoleMenu(role);
        return role;
    }

    @Override
    public void deleteRoleById(String roleId) {
        SysRole role = new SysRole(roleId);
        sysRoleMapper.deleteById(roleId);
        sysRoleMapper.deleteRoleMenu(role);
        sysRoleMapper.deleteRoleOrg(role);
        sysRoleMapper.deleteRoleUser(role);
    }

    @Override
    public void deleteRoleUser(SysRole role) {
        sysRoleMapper.deleteRoleUser(role);
    }

    @Override
    public void assignRole(SysRole role) {
        sysRoleMapper.assignRole(role);
    }

    @Override
    public Boolean isRoleCodeExists(SysRole role) {
        return sysRoleMapper.isCodeExists(role) > 0 ;
    }

    @Override
    public SysUser getUserById(String id) {
        return sysUserMapper.get(id);
    }

    @Override
    public PageInfo<SysUser> findUserPage(Paging page, SysUser user) {
        // 执行分页查询
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
        List<SysUser> list = sysUserMapper.findList(user);
        if(CollUtil.isNotEmpty(list)) {
            Map<String, SysDict> dictMap = sysDictMapper.findMapByParentCodes(new String[]{"STATE", "USER_TYPE"});
            for (SysUser sysUser : list) {
                if(dictMap.get(sysUser.getType()) != null) {
                    sysUser.setTypeDesc(dictMap.get(sysUser.getType()).getLabel());
                }
                if(dictMap.get(sysUser.getState()) != null) {
                    sysUser.setStateDesc(dictMap.get(sysUser.getState()).getLabel());
                }
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public List<SysUser> getUsersByRoleId(String roleId) {
        List<SysUser> list = sysUserMapper.getUsersByRoleId(roleId);
        if(CollUtil.isNotEmpty(list)) {
            Map<String, SysDict> dictMap = sysDictMapper.findMapByParentCodes(new String[]{"STATE", "USER_TYPE"});
            for (SysUser sysUser : list) {
                if(dictMap.get(sysUser.getType()) != null) {
                    sysUser.setTypeDesc(dictMap.get(sysUser.getType()).getLabel());
                }
                if(dictMap.get(sysUser.getState()) != null) {
                    sysUser.setStateDesc(dictMap.get(sysUser.getState()).getLabel());
                }
            }
        }
        return list;
    }

    @Override
    public Boolean isUserExists(SysUser user) {
        return sysUserMapper.isExists(user) > 0;
    }

    @Override
    public SysUser saveUser(SysUser user) {
        if (user.getIsNewRecord()) {
            user.preInsert();
            sysUserMapper.insert(user);
        } else {
            // 更新用户数据
            user.preUpdate();
            sysUserMapper.update(user);
        }
        // 更新用户与角色关联
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            sysUserMapper.deleteUserRole(user);
            sysUserMapper.insertUserRole(user);
        }

        return user;
    }

    @Override
    public void updateUserStates(Map<String, String> param) {
        sysUserMapper.updateUserStates(param);
    }

    @Override
    public void deleteUserById(String userId) {
        sysUserMapper.deleteUserRole(new SysUser(userId));
        sysUserMapper.deleteById(userId);
    }

    @Override
    public void updateUserPasswordById(String userId, String newPassword, Date lastPasswordResetDate) {
        SysUser user = new SysUser(userId);
        user.setPassword(newPassword);
        user.setLastPasswordResetDate(lastPasswordResetDate);
        sysUserMapper.updatePasswordById(user);
    }

}
