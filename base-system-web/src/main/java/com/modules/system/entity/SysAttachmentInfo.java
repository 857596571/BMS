package com.modules.system.entity;

import com.common.api.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统附件表
 *
 * @author xmh
 * @email 
 * @date 2018-07-29 10:41:23
 */
@ApiModel(value = "SysAttachmentInfo", description = "系统附件表")
public class SysAttachmentInfo extends DataEntity {

    /** 业务ID */
    @ApiModelProperty(value="业务ID", name="bizId")
    private String bizId;

    /** 业务类型 */
    @ApiModelProperty(value="业务类型", name="bizType")
    private String bizType;

    /** 文件名称 */
    @ApiModelProperty(value="文件名称", name="fileName")
    private String fileName;

    /** 文件路径 */
    @ApiModelProperty(value="文件路径", name="filePath")
    private String filePath;

    public SysAttachmentInfo(String bizId, String bizType, String fileName, String filePath) {
        this.bizId = bizId;
        this.bizType = bizType;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    /**
     * 设置：业务ID
     */
    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    /**
     * 获取：业务ID
     */
    public String getBizId() {
        return bizId;
    }

    /**
     * 设置：业务类型
     */
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    /**
     * 获取：业务类型
     */
    public String getBizType() {
        return bizType;
    }

    /**
     * 设置：文件名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获取：文件名称
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置：文件路径
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 获取：文件路径
     */
    public String getFilePath() {
        return filePath;
    }

}
