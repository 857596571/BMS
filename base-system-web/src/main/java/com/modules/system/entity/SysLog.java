package com.modules.system.entity;

import com.common.api.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 操作日志
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SysLog extends DataEntity {

    /**
     * 操作描述
     */
    private String description;
    /**
     * 操作用户
     */
    private String username;
    /**
     * 操作时间
     */
    private Long startTime;
    /**
     * 消耗时间
     */
    private Integer spendTime;
    /**
     * 根路径
     */
    private String basePath;
    /**
     * URI
     */
    private String uri;
    /**
     * URL
     */
    private String url;
    /**
     * 请求类型
     */
    private String method;
    /**
     * 请求参数
     */
    private String parameter;
    /**
     * 用户标识
     */
    private String userAgent;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 结果
     */
    private String result;
    /**
     * 权限值
     */
    private String permissions;
}
