package com.modules.system.entity;


import com.common.api.DataEntity;

import java.util.List;

/**
 * 角色
 */
public class SysRole extends DataEntity {

    /**
     * 角色编码
     */
    private String code;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色类型[1:管理角色，2:普通角色]
     */
    private Integer type;
    /**
     * 状态[1:在用，0:停用]
     */
    private Integer state;
    /**
     * 等级[1:系统，2:业务]
     */
    private Integer level;
    /**
     * 数据范围[1：所有数据；2：所在机构及以下数据；3：所在机构数据；4：仅本人数据；5：按明细设置]
     */
    private Integer dataScope;
    /**
     * 拥有菜单列表
     */
    private List<SysMenu> menus;
    /**
     * 按明细设置数据范围
     */
    private List<SysOrg> orgs;

    public SysRole() {}

    public SysRole(String id) {
        super(id);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getDataScope() {
        return dataScope;
    }

    public void setDataScope(Integer dataScope) {
        this.dataScope = dataScope;
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
}
