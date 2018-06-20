package com.modules.config;

import com.common.security.config.AbstractWebSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.web.cors.CorsUtils;

/**
 * spring-security配置
 *
 * @author dcp
 */
@Configuration
public class WebSecurityConfig extends AbstractWebSecurityConfig {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/sys/task/**");
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
            .authorizeRequests()
            .antMatchers("/auth/token").permitAll();
        super.configure(security);
    }
}
