package com.modules.system.entity;

import java.util.Date;

import com.common.api.DataEntity;

/**
 * 通知公告表
 */
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

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getInfoSources() {
        return infoSources;
    }

    public void setInfoSources(String infoSources) {
        this.infoSources = infoSources;
    }

    public String getSourcesUrl() {
        return sourcesUrl;
    }

    public void setSourcesUrl(String sourcesUrl) {
        this.sourcesUrl = sourcesUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReaderTimes() {
        return readerTimes;
    }

    public void setReaderTimes(Integer readerTimes) {
        this.readerTimes = readerTimes;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
