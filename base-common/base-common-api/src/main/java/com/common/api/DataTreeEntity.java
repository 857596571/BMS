package com.common.api;

/**
 * 数据TreeEntity类
 */
public abstract class DataTreeEntity extends DataEntity {

    /**
     * 父ID
     */
    private Integer parentId;
    /**
     * 左边界值
     */
    private Integer leftNum;
    /**
     * 右边界值
     */
    private Integer rightNum;
    /**
     * 节点数
     */
    private Integer treeNodeNum;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getLeftNum() {
        return leftNum;
    }

    public void setLeftNum(Integer leftNum) {
        this.leftNum = leftNum;
    }

    public Integer getRightNum() {
        return rightNum;
    }

    public void setRightNum(Integer rightNum) {
        this.rightNum = rightNum;
    }

    public Integer getTreeNodeNum() {
        return treeNodeNum;
    }

    public void setTreeNodeNum(Integer treeNodeNum) {
        this.treeNodeNum = treeNodeNum;
    }
}
