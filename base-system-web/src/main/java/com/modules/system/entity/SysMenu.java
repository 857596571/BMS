package com.modules.system.entity;

import com.common.api.DataTreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单Entity
 */
@Data
@EqualsAndHashCode(callSuper=false)
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

}