package com.modules.system.security.utils;

import cn.hutool.json.JSONUtil;
import com.common.security.AbstractTokenUtil;
import com.modules.system.security.model.AuthUser;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Token util.
 *
 * @author dcp
 */
@Component
@ConfigurationProperties("system.yml.security.jwt")
public class TokenUtil extends AbstractTokenUtil {

    @Override
    public UserDetails getUserDetails(String token, HttpServletRequest request) {
        String userDetailsString = getUserDetailsString(token, getIdKey(request));
        if (userDetailsString != null) {
            return JSONUtil.toBean(userDetailsString, AuthUser.class);
        }
        return null;
    }

}