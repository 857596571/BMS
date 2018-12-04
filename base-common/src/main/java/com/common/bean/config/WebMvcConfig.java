package com.common.bean.config;

import com.common.bean.resolver.TokenArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * mvc配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private YmlConfig ymlConfig;


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new TokenArgumentResolver());
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/files/** 访问都映射到classpath:/static/ 目录下
        String fileRootPath = (String)this.ymlConfig.getMap("upload", String.class).get("file-root-path");
        registry.addResourceHandler("/files/**").addResourceLocations("file:" + fileRootPath);
    }
}
