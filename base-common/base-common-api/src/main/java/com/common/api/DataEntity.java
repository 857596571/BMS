package com.common.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 数据Entity类
 */
@Getter
@Setter
@ToString
public abstract class DataEntity extends BaseEntity {

    /**
     * 删除标记0：正常
     */
    public static final String DEL_FLAG_NORMAL = "0";
    /**
     * 删除标记1：删除
     */
    public static final String DEL_FLAG_DELETE = "1";

    /**
     * 搜索
     */
    private String searchKeys;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 更新日期
     */
    private Date updateDate;
    /**
     * 删除标记(0:正常;1:删除;)
     */
    private String delFlag;
    /**
     * 创建者
     */
    private String createUserId;
    /**
     * 更新者
     */
    private String updateUserId;
    /**
     * 备注
     */
    private String remarks;

    public DataEntity() {
        super();
        this.delFlag = DEL_FLAG_NORMAL;
    }

    public DataEntity(String id) {
        super(id);
    }

    /**
     * 插入之前执行方法，需要手动调用
     */
    @Override
    public void preInsert() {
        // 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
//        if (this.getIsNewRecord()) {
//            setId(RandomHelper.uuid());
//        }
        this.delFlag= DEL_FLAG_NORMAL;
        this.updateDate = new Date();
        this.createDate = this.updateDate;
    }

    /**
     * 插入之前执行方法，需要手动调用
     */
    @Override
    public void preInsert(String userId) {
        // 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
//        if (this.getIsNewRecord()) {
//            setId(RandomHelper.uuid());
//        }
        this.delFlag="0";
        this.updateDate = new Date();
        this.createDate = this.updateDate;
        this.updateUserId = userId;
        this.createUserId = userId;
    }

    /**
     * 更新之前执行方法，需要手动调用
     */
    @Override
    public void preUpdate() {
        this.updateDate = new Date();
    }
    /**
     * 更新之前执行方法，需要手动调用
     */
    @Override
    public void preUpdate(String userId) {
        this.updateDate = new Date();
        this.updateUserId = userId;
    }

}
