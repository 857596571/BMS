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
        web.ignoring().antMatchers(
                "/sys/file/**",
                "/files/**",
                "/webjars/**",
                "/v2/api-docs",
                // swagger api json
                "/swagger-resources/configuration/ui",
                // 用来获取支持的动作
                "/swagger-resources",
                //用来获取api-docs的URI
                "/swagger-resources/configuration/security",
                // 安全选项
                "/swagger-ui.html");
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
            .authorizeRequests()
            .antMatchers("/auth/token", "/files/**").permitAll();
        super.configure(security);
    }
}
