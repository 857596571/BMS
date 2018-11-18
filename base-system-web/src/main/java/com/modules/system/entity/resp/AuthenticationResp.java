package com.modules.system.entity.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 鉴权返回对象
 */
@Data
@ApiModel(value = "AuthenticationResp", description = "鉴权返回对象")
public class AuthenticationResp implements Serializable {

    /**
     * token码
     */
    @ApiModelProperty(value="token码", name="access_token")
    private String access_token;
    /**
     * token有效时间
     */
    @ApiModelProperty(value="token有效时间", name="expires_in")
    private Long expires_in;
    /**
     * token类型
     */
    @ApiModelProperty(value="token类型", name="token_type")
    private String token_type;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
}
