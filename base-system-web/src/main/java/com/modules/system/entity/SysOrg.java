package com.modules.system.entity;

import com.common.api.DataTreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;


/**
 * 机构
 */
@Data
@EqualsAndHashCode(callSuper=false)
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
