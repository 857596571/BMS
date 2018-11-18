package com.modules.system.entity;

import com.common.api.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 通知公告表
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SysNotice extends DataEntity {

    /**
     * 公告标题
     */
    private String noticeTitle;
    /**
     * 公告类型
     */
    private String noticeType;
    /**
     * 发布时间
     */
    private Date sendTime;
    /**
     * 信息来源
     */
    private String infoSources;
    /**
     * 来源地址
     */
    private String sourcesUrl;
    /**
     * 内容
     */
    private String content;
    /**
     * 阅读次数
     */
    private Integer readerTimes;
    /**
     * 发布状态
     */
    private String state;
}
