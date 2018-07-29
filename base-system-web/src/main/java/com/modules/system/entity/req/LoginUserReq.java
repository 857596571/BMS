package com.modules.system.entity.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 用户登录请求
 */
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
}
