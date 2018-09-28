package com.modules.system.entity;

import com.common.api.DataTreeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单Entity
 */
public class SysMenu extends DataTreeEntity {

    /**
     * 名称
     */
    private String        userId;
    /**
     * 名称
     */
    private String        name;
    /**
     * 链接
     */
    private String        href;
    /**
     * 图标
     */
    private String        icon;
    /**
     * 排序
     */
    private Integer       sort;
    /**
     * 状态
     */
    private String        state;
    /**
     * 状态
     */
    private String        stateDesc;
    /**
     * 级别
     */
    private String        level;
    /**
     * 级别描述
     */
    private String        levelDesc;
    /**
     * 权限标识
     */
    private String        permission;
    /**
     * 子节点
     */
    private List<SysMenu> children;

    public SysMenu() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
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
     * 添加子节点
     * @param node
     */
    public void addChild(SysMenu node) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(node);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}