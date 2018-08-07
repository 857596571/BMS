package com.modules.quartz.entity;

import com.common.api.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任务调度运行日志
 *
 * @author xmh
 * @email 
 * @date 2018-07-22 00:03:21
 */
@ApiModel(value = "QuartzTaskLog", description = "任务调度运行日志")
public class QuartzTaskLog extends DataEntity {

    /** 任务ID */
    @ApiModelProperty(value="任务ID", name="quartzId")
    private String quartzId;

    /** 任务执行BEAN */
    @ApiModelProperty(value="任务执行BEAN", name="beanId")
    private String beanId;

    /** 任务运行参数 */
    @ApiModelProperty(value="任务运行参数", name="params")
    private String params;

    /** 执行状态 */
    @ApiModelProperty(value="执行状态", name="state")
    private String state;

    /** 执行状态描述 */
    @ApiModelProperty(value="执行状态描述", name="stateDesc")
    private String stateDesc;

    /** 失败信息 */
    @ApiModelProperty(value="失败信息", name="error")
    private String error;

    /** 耗时(单位：毫秒) */
    @ApiModelProperty(value="耗时(单位：毫秒)", name="times")
    private Long times;


    /**
     * 设置：任务ID
     */
    public void setQuartzId(String quartzId) {
        this.quartzId = quartzId;
    }

    /**
     * 获取：任务ID
     */
    public String getQuartzId() {
        return quartzId;
    }

    /**
     * 设置：任务执行BEAN
     */
    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    /**
     * 获取：任务执行BEAN
     */
    public String getBeanId() {
        return beanId;
    }

    /**
     * 设置：任务运行参数
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * 获取：任务运行参数
     */
    public String getParams() {
        return params;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    /**
     * 设置：失败信息
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * 获取：失败信息
     */
    public String getError() {
        return error;
    }

    /**
     * 设置：耗时(单位：毫秒)
     */
    public void setTimes(Long times) {
        this.times = times;
    }

    /**
     * 获取：耗时(单位：毫秒)
     */
    public Long getTimes() {
        return times;
    }

}
