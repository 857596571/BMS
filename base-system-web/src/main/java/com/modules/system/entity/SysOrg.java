package com.modules.system.entity;

import com.common.api.DataTreeEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * 机构
 */
public class SysOrg extends DataTreeEntity {

    /**
     * 名称
     */
    private String name;
    /**
     * 名称拼音
     */
    private String namePy;
    /**
     * 编码
     */
    private String code;
    /**
     * 排序号
     */
    private Integer sort;
    /**
     * 状态[1:在用，0:停用]
     */
    private String state;
    /**
     * 状态描述
     */
    private String stateDesc;
    /**
     * 类型
     */
    private String type;
    /**
     * 类型描述
     */
    private String typeDesc;
    /**
     * 级别
     */
    private String level;
    /**
     * 级别描述
     */
    private String levelDesc;
    /**
     * 子节点
     */
    private List<SysOrg> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamePy() {
        return namePy;
    }

    public void setNamePy(String namePy) {
        this.namePy = namePy;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevelDesc() {
        return levelDesc;
    }

    public void setLevelDesc(String levelDesc) {
        this.levelDesc = levelDesc;
    }

    public List<SysOrg> getChildren() {
        return children;
    }

    public void setChildren(List<SysOrg> children) {
        this.children = children;
    }

    /**
     * 添加子节点
     * @param node
     */
    public void addChild(SysOrg node) {
        if(this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(node);
    }
}
