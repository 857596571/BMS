package com.modules.system.entity.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求
 */
@Data
@ApiModel(value = "LoginUserReq", description = "用户登录请求")
public class LoginUserReq implements Serializable {

    /**
     * 用户名
     */
    @ApiModelProperty(value="loginName", name="用户名")
    private String loginName;
    /**
     * 密码
     */
    @ApiModelProperty(value="password", name="密码")
    private String password;

    /**
     * 登录子系统
     */
    @ApiModelProperty(value="loginSystem", name="登录子系统")
    private String loginSystem;


    public LoginUserReq() {
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginSystem() {
        return loginSystem;
    }

    public void setLoginSystem(String loginSystem) {
        this.loginSystem = loginSystem;
    }
}
