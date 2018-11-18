package com.modules.system.entity;

import com.common.api.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统附件表
 *
 * @author xmh
 * @email 
 * @date 2018-07-29 10:41:23
 */
@Data
@EqualsAndHashCode(callSuper=false)
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

    public SysAttachmentInfo() {
    }

    public SysAttachmentInfo(String bizId, String bizType, String fileName, String filePath) {
        this.bizId = bizId;
        this.bizType = bizType;
        this.fileName = fileName;
        this.filePath = filePath;
    }

}
