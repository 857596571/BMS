package com.common.api;


import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Entity 基类
 */
@Getter
@Setter
@ToString
public abstract class BaseEntity extends QueryWrapper implements Serializable {

    /**
     * 实体编号（唯一标识）
     */
    private String id;

    /**
     * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     */
    private boolean isNewRecord = true;

    /**
     * 自定义SQL（SQL标识，SQL内容）
     */
    protected String sqlMap;

    public BaseEntity() {

    }

    public BaseEntity(String id) {
        this();
        this.id = id;
    }



    /**
     * 插入之前执行方法，子类实现
     */
    public abstract void preInsert();

    /**
     * 更新之前执行方法，子类实现
     */
    public abstract void preUpdate();

    /**
     * 插入之前执行方法，子类实现
     */
    public abstract void preInsert(String userId);

    /**
     * 更新之前执行方法，子类实现
     */
    public abstract void preUpdate(String userId);

    /**
     * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     *
     * @return 是否是新记录
     */
    @JsonIgnore
    public boolean getIsNewRecord() {
        return StrUtil.isBlank(getId());
    }

}
