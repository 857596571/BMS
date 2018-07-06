package com.modules.system.service.impl;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
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
@SuppressWarnings("ALL") @Service
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

    /** 缓存对象 */
    private Cache<String, List<SysDict>> dictCache = CacheUtil.newLFUCache(100);


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
//        List<SysDict> list = dictCache.get(parentCode);
//        if(list == null || list.isEmpty()) {
//            Map<String, Object> query = new HashMap<>();
//            query.put("parentCode", parentCode);
//            list = sysDictMapper.findList(query);
//            dictCache.put(parentCode, list);
//        }
        Map<String, Object> query = new HashMap<>();
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
        return DictUtils.makeTree(list);
    }

    @Override
    public Boolean isDictCodeExists(SysDict sysDict) {
        return sysDictMapper.isCodeExists(sysDict) > 0;
    }

    @SuppressWarnings("AlibabaTransactionMustHaveRollback") @Override
    @Transactional
    public SysDict saveDict(SysDict sysDict) {
        if(sysDict.getIsNewRecord()) {
            int rightNum = sysDictMapper.getLRNum(sysDict);
            if(rightNum != 0 && sysDict.getParentId() != 0) {
                SysDict param = new SysDict();
                param.setRightNum(rightNum);
                param.setDelFlag("0");
                //如果不是根节点，才更新左右值
                sysDictMapper.updateLNum(param);
                sysDictMapper.updateRNum(param);
            }
            sysDict.setLeftNum(rightNum + 1);
            sysDict.setRightNum(rightNum + 2);
            sysDictMapper.insert(sysDict);
        } else {
            sysDictMapper.update(sysDict);
        }
        return sysDict;
    }

    @Override
    @Transactional
    public void updateDictSorts(List<SysDict> list) {
        for (SysDict sysDict : list) {
            sysDictMapper.update(sysDict);
        }
        dictCache.clear();
    }

    @Override
    @Transactional
    public void updateDictStates(Map<String, String> param) {
        sysDictMapper.updateStates(param);
        dictCache.clear();
    }

    @Override
    public void deleteDictById(SysDict sysDict) {
        //计算节点数量
        sysDict.setTreeNodeNum(sysDict.getRightNum() - sysDict.getLeftNum() + 1);
        sysDict.setDelFlag("1");
        sysDictMapper.updateLNum(sysDict);
        sysDictMapper.updateRNum(sysDict);
        sysDictMapper.deleteById(sysDict.getId());
        dictCache.clear();
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
    public List<SysMenu> findMenuList(String userId) {
        List<SysMenu> resultList = new ArrayList<>();
        //按父子顺序排列菜单列表
        MenuUtils.sortList(resultList, getMenuListByUserId(userId), "0");
        return resultList;
    }

    @Override
    @Transactional()
    public SysMenu saveMenu(SysMenu menu) {
        if(menu.getIsNewRecord()) {
            int rightNum = sysMenuMapper.getLRNum(menu);
            menu.setLeftNum(rightNum + 1);
            menu.setRightNum(rightNum + 2);
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
    @Transactional
    public void updateMenuSorts(List<SysMenu> list) {
        for (SysMenu sysMenu : list) {
            sysMenuMapper.update(sysMenu);
        }
    }

    @Override
    @Transactional()
    public void deleteMenuById(SysMenu menu) {
        //计算节点数量
        menu.setTreeNodeNum(menu.getRightNum() - menu.getLeftNum() + 1);
        menu.setDelFlag("1");
        sysMenuMapper.updateLNum(menu);
        sysMenuMapper.updateRNum(menu);
        sysMenuMapper.deleteById(menu.getId());
    }

    @Override
    public List<SysOrg> findOrgList(SysOrg org) {
        List<SysOrg> list = OrgUtils.makeTree(sysOrgMapper.findList(org));
        if(CollUtil.isNotEmpty(list)) {
            Map<String, SysDict> dictMap = sysDictMapper.findMapByParentCodes(new String[]{"1", "2"});
            for (SysOrg sysOrg : list) {
                if(dictMap.get(sysOrg.getLevel()) != null) {
                    sysOrg.setLevelDesc(dictMap.get(sysOrg.getLevel()).getLabel());
                }
                if(dictMap.get(sysOrg.getType()) != null) {
                    sysOrg.setTypeDesc(dictMap.get(sysOrg.getType()).getLabel());
                }
            }
        }
        return list;
    }

    @Override
    public Boolean isOrgCodeExists(SysOrg org) {
        return sysOrgMapper.isCodeExists(org) > 0;
    }

    @Override
    @Transactional
    public SysOrg saveOrg(SysOrg org) {
        if(org.getIsNewRecord()) {
            Integer rightNum = sysOrgMapper.getLRNum(org);
            rightNum = rightNum != null ? rightNum : 0;
            org.setLeftNum(rightNum );
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
                sysOrgMapper.update(sysOrg);
            }
        }
    }

    @Override
    public void deleteOrgById(String id) {
//        SysOrg org = sysOrgMapper.get(id);
//        //计算节点数量
//        org.setTreeNodeNum(org.getRightNum() - org.getLeftNum() + 1);
//        org.setDelFlag("1");
//        sysOrgMapper.updateLNum(org);
//        sysOrgMapper.updateRNum(org);
        sysOrgMapper.deleteById(id);
    }

    @Override
    public List<SysRole> findRoleList(SysRole role) {
        return sysRoleMapper.findList(role);
    }

    @Override
    @Transactional()
    public SysRole saveRole(SysRole role) {
        if (role.getIsNewRecord()) {
            role.preInsert();
            sysRoleMapper.insert(role);
        } else {
            // 更新角色数据
            role.preUpdate();
            sysRoleMapper.update(role);
            if(StrUtil.equals(role.getDataScope(), "5")) {
                sysRoleMapper.deleteRoleOrg(role);
            }
        }
        if(CollUtil.isNotEmpty(role.getOrgs())) {
            //更新角色数据权限
            sysRoleMapper.insertRoleOrg(role);
        }
        return role;
    }

    @Override
    public SysRole saveRoleMenuAuth(SysRole role) {
        sysRoleMapper.deleteRoleMenu(role);
        sysRoleMapper.insertRoleMenu(role);
        return role;
    }

    @Override
    @Transactional()
    public void deleteRoleById(String roleId) {
        SysRole role = new SysRole(roleId);
        sysRoleMapper.deleteById(roleId);
        sysRoleMapper.deleteRoleMenu(role);
        sysRoleMapper.deleteRoleOrg(role);
        sysRoleMapper.deleteRoleUser(role);
    }

    @Override
    @Transactional
    public void deleteRoleUser(SysRole role) {
        sysRoleMapper.deleteRoleUser(role);
    }

    @Override
    @Transactional
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
        return new PageInfo<>(list);
    }

    @Override
    public List<SysUser> getUsersByRoleId(String roleId) {
        return sysUserMapper.getUsersByRoleId(roleId);
    }

    @Override
    @Transactional()
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
    @Transactional()
    public void deleteUserById(String userId) {
        sysUserMapper.deleteById(userId);
    }

    @Override
    @Transactional()
    public void updateUserPasswordById(String userId, String newPassword, Date lastPasswordResetDate) {
        SysUser user = new SysUser(userId);
        user.setPassword(newPassword);
        user.setLastPasswordResetDate(lastPasswordResetDate);
        sysUserMapper.updatePasswordById(user);
    }

}
