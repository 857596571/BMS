package com.modules.system.log.aop;

import com.modules.system.entity.SysLog;
import com.modules.system.service.SysLogService;
import org.aspectj.lang.annotation.Aspect;


import com.common.utils.IPUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


/**
 * 系统日志，切面处理类
 */
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private SysLogService sysLogService;

    private static final org.slf4j.Logger _log = org.slf4j.LoggerFactory.getLogger(SysLogAspect.class);

    // 开始时间
    private long startTime = 0L;
    // 结束时间
    private long endTime = 0L;

    @Before("execution(* *..controller..*.*(..))")
    public void doBeforeInServiceLayer(JoinPoint joinPoint) {
        _log.debug("doBeforeInServiceLayer");
        startTime = System.currentTimeMillis();
    }

    @After("execution(* *..controller..*.*(..))")
    public void doAfterInServiceLayer(JoinPoint joinPoint) {
        _log.debug("doAfterInServiceLayer");
    }

    @Around("execution(* *..controller..*.*(..))")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        // 获取request
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();

           SysLog sysLog = new SysLog();
        // 从注解中获取操作名称、获取响应结果
            Object result = pjp.proceed();
            Signature signature = pjp.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            if (method.isAnnotationPresent(ApiOperation.class)) {
                ApiOperation log = method.getAnnotation(ApiOperation.class);
                sysLog.setDescription(log.value());
            }
            if (method.isAnnotationPresent(PreAuthorize.class)) {
                PreAuthorize requiresPermissions = method.getAnnotation(PreAuthorize.class);
                String permissions = requiresPermissions.value();
                sysLog.setPermissions(permissions);
            }
            endTime = System.currentTimeMillis();
            _log.debug("doAround>>>resp={},耗时：{}", result, endTime - startTime);

            sysLog.setBasePath(IPUtils.getBasePath(request));
            sysLog.setIp(IPUtils.getIpAddr(request));
            sysLog.setMethod(request.getMethod());
            if (request.getMethod().equalsIgnoreCase("GET")) {
                sysLog.setParameter(request.getQueryString());
            } else {
                sysLog.setParameter(ObjectUtils.toString(request.getParameterMap()));
            }
            sysLog.setResult(ObjectUtils.toString(result));
            sysLog.setSpendTime((int) (endTime - startTime));
            sysLog.setStartTime(startTime);
            sysLog.setUri(request.getRequestURI());
            sysLog.setUrl(ObjectUtils.toString(request.getRequestURL()));
            sysLog.setUserAgent(request.getHeader("User-Agent"));
            if (request.getUserPrincipal() != null) {
                sysLog.setUsername(ObjectUtils.toString(request.getUserPrincipal().getName()));
            }
            sysLogService.save(sysLog);
            return result;

    }

}
