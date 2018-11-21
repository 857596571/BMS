package com.modules.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 获取用户信息也是通过这个应用实现
 * 这里既是认证服务器，也是资源服务器
 * EnableResourceServer
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.modules.auth", "com.common.bean"})
public class BaseAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseAuthServerApplication.class, args);
    }

}
