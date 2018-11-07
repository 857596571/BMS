package com;

import com.common.web.util.SpringContextHolder;
import com.common.web.util.YmlConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The type Web admin application.
 *
 * @author xmh
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableTransactionManagement //启用事务
@MapperScan("com.modules.*.mapper")
@Import(value = {SpringContextHolder.class, YmlConfig.class})
public class WebApplication extends SpringBootServletInitializer {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(WebApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
