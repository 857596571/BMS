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
     * 角色类型
     */
    private String type;
    /**
     * 角色类型描述
     */
    private String typeDesc;
    /**
     * 状态
     */
    private String state;
    /**
     * 状态描述
     */
    private String stateDesc;
    /**
     * 等级
     */
    private String level;
    /**
     * 等级描述
     */
    private String levelDesc;
    /**
     * 数据范围[1：所有数据；2：所在机构及以下数据；3：所在机构数据；4：仅本人数据；5：按明细设置]
     */
    private String dataScope;
    /**
     * 数据范围描述
     */
    private String dataScopeDesc;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDataScope() {
        return dataScope;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
    }

    /**
     * Getter method for property <tt>typeDesc</tt>.
     *
     * @return property value of typeDesc
     */
    public String getTypeDesc() {
        return typeDesc;
    }

    /**
     * Setter method for property <tt>typeDesc</tt>.
     *
     * @param typeDesc value to be assigned to property typeDesc
     */
    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
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

    /**
     * Getter method for property <tt>levelDesc</tt>.
     *
     * @return property value of levelDesc
     */
    public String getLevelDesc() {
        return levelDesc;
    }

    /**
     * Setter method for property <tt>levelDesc</tt>.
     *
     * @param levelDesc value to be assigned to property levelDesc
     */
    public void setLevelDesc(String levelDesc) {
        this.levelDesc = levelDesc;
    }

    /**
     * Getter method for property <tt>dataScopeDesc</tt>.
     *
     * @return property value of dataScopeDesc
     */
    public String getDataScopeDesc() {
        return dataScopeDesc;
    }

    /**
     * Setter method for property <tt>dataScopeDesc</tt>.
     *
     * @param dataScopeDesc value to be assigned to property dataScopeDesc
     */
    public void setDataScopeDesc(String dataScopeDesc) {
        this.dataScopeDesc = dataScopeDesc;
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
