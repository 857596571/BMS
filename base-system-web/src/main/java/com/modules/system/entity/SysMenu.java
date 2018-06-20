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
    private String name;
    /**
     * 链接
     */
    private String href;
    /**
     * 图标
     */
    private String icon;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态[1:显示，0:不显示]
     */
    private Integer state;
    /**
     * 级别[1:菜单，2:功能]
     */
    private Integer level;
    /**
     * 权限标识
     */
    private String permission;
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
     * 添加子节点
     * @param node
     */
    public void addChild(SysMenu node) {
        if(this.children == null) this.children = new ArrayList<>();
        this.children.add(node);
    }
}