package com.modules.config;

import com.common.web.util.YmlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 静态资源映射配置
 */
@Configuration
public class WebMvcStaticConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private YmlConfig ymlConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/files/** 访问都映射到classpath:/static/ 目录下
        String fileRootPath = (String)this.ymlConfig.getMap("upload", String.class).get("file-root-path");
        registry.addResourceHandler("/files/**").addResourceLocations("file:" + fileRootPath);
    }

}
