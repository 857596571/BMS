package com.common.datasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
@Order(-1)
public class DataSourceAspect {

//    @Pointcut("execution(* com..*.*(..))")
//    private void db1Aspect() {
//    }
//
//    @Pointcut("execution(* com..*.*(..))")
//    private void db2Aspect() {
//    }
//
//    @Before( "db1Aspect()" )
//    public void db1() {
//        log.info("切换到db1 数据源...");
//        DataSourceContextHolder.setDataSource(DataSourceEnum.DB1.getValue());
//    }
//
//    @Before("db2Aspect()" )
//    public void db2 () {
//        log.info("切换到db2 数据源...");
//        DataSourceContextHolder.setDataSource(DataSourceEnum.DB1.getValue());
//    }
}