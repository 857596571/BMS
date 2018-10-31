package com.common.api;

import lombok.Data;

/**
 * 数据TreeEntity类
 */
@Data
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

}
