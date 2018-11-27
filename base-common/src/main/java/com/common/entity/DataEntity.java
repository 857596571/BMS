package com.common.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 数据Entity类
 */
@Data
@EqualsAndHashCode(callSuper=false)
public abstract class DataEntity extends Model {

    /**
     * 删除标记0：正常
     */
    public static final String DEL_FLAG_NORMAL = "0";
    /**
     * 删除标记1：删除
     */
    public static final String DEL_FLAG_DELETE = "1";

    /**
     * 主键
     */
    private Long id;
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
    private String createBy;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 备注
     */
    private String remarks;

    public DataEntity() {
        super();
        this.delFlag = DEL_FLAG_NORMAL;
    }

    public DataEntity(Long id) {
        this.id = id;
    }

    public boolean getIsNewRecord() {
        return id == null;
    }

}
