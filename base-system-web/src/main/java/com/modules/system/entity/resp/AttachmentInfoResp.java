package com.modules.system.entity.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 附件返回对象
 */
@ApiModel(value = "AttachmentInfoResp", description = "附件返回对象")
public class AttachmentInfoResp implements Serializable {

    /**
     * 业务ID
     */
    @ApiModelProperty(value="业务ID", name="bizId")
    private String bizId;
    /**
     * 业务类型
     */
    @ApiModelProperty(value="业务类型", name="bizType")
    private String bizType;
    /**
     * 文件名称
     */
    @ApiModelProperty(value="文件名称", name="fileName")
    private String fileName;
    /**
     * 文件路径
     */
    @ApiModelProperty(value="文件路径", name="filePath")
    private String filePath;

    public AttachmentInfoResp() {
    }

    public AttachmentInfoResp(String bizType, String fileName, String filePath) {
        this.bizType = bizType;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
