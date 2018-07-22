package com.modules.quartz.entity;

import com.common.api.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任务调度资源
 *
 * @author xmh
 * @email 
 * @date 2018-07-22 00:03:20
 */
@ApiModel(value = "QuartzTask", description = "任务调度资源")
public class QuartzTask extends DataEntity {

    /** 任务名称 */
    @ApiModelProperty(value="任务名称", name="name")
    private String name;

    /** 任务执行BEAN */
    @ApiModelProperty(value="任务执行BEAN", name="beanId")
    private String beanId;

    /** 任务运行参数 */
    @ApiModelProperty(value="任务运行参数", name="params")
    private String params;

    /** cron表达式 */
    @ApiModelProperty(value="cron表达式", name="cronExpression")
    private String cronExpression;

    /** 任务状态 */
    @ApiModelProperty(value="任务状态", name="state")
    private String state;

    /** 任务状态描述 */
    @ApiModelProperty(value="任务状态描述", name="stateDesc")
    private String stateDesc;


    /**
     * 设置：任务名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：任务名称
     */
    public String getName() {
        return name;
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

    /**
     * 设置：cron表达式
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * 获取：cron表达式
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * 设置：任务状态
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 获取：任务状态
     */
    public String getState() {
        return state;
    }

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }
}
