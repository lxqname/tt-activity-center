/**
 * LogAspect  2019/3/27
 *
 * Copyright (c) 2018, DEEPEXI Inc. All rights reserved.
 * DEEPEXI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.deepexi.activity.aop;

import com.alibaba.fastjson.JSON;
import com.deepexi.activity.extension.AppRuntimeEnv;
import com.deepexi.common.annotation.TenantId;
import com.deepexi.common.annotation.Token;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 日志统一打印切面
 *
 * @author admin
 */
@Component
@Aspect
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private final static String TENANT_KEY = "tenantId";
    private final static String TOKEN_KEY = "Authorization";

    @Resource
    private AppRuntimeEnv appRuntimeEnv;

    @Pointcut("execution (* com.deepexi.activity.controller..*.*(..))")
    public void controllerLogAop() {
    }

    @Pointcut("execution (* com.deepexi.activity.service..*.*(..))")
    public void apiLogAop() {
    }

    @Before("controllerLogAop()")
    public void setAppRuntimeEnv(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method == null || RequestContextHolder.getRequestAttributes() == null) {
            appRuntimeEnv.ensureTenantId(null);
        }

        HttpServletRequest request = null;
        if (RequestContextHolder.getRequestAttributes() != null) {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }

        TenantId tenantAnnotation = null;
        Token tokenAnnotation = null;
        if(method != null){
            tenantAnnotation = method.getAnnotation(TenantId.class);
            tokenAnnotation = method.getAnnotation(Token.class);
        }

        //默认全局拦截，不包含注解，或者require=true，则拦截
        if (tenantAnnotation == null || tenantAnnotation.require()) {
            appRuntimeEnv.ensureTenantId(getParam(request, TENANT_KEY));
        } else {
            appRuntimeEnv.setTenantId(getParam(request, TENANT_KEY));
        }

        //token统一获取,eg:Authorization：Bearer xxxx
        String auth = request.getHeader(TOKEN_KEY);
        if (tokenAnnotation == null || tokenAnnotation.require()) {
            appRuntimeEnv.ensureToken(StringUtils.isEmpty(auth) ? null : auth.split(" ")[1].trim());
        } else {
            appRuntimeEnv.setToken(StringUtils.isEmpty(auth) ? null : auth.split(" ")[1].trim());
        }
    }

    @Around("apiLogAop()")
    public Object aroundApi(ProceedingJoinPoint point) throws Throwable {
        logger.info("日志统一打印 ===== {}.{}() start =====,参数:\n{}", point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), argsToString(point.getArgs()));
        DateTime startTime = new DateTime();
        DateTime endTime = null;
        Interval interval = null;
        Object response = null;

        try {
            //执行该方法
            response = point.proceed();
        } catch (Exception e) {
            endTime = new DateTime();
            interval = new Interval(startTime, endTime);
            logger.info("日志统一打印 ===== {}.{}() end =====,响应时间:{}毫秒,响应内容:\n{}", point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), interval.toDurationMillis());
            throw e;
        }
        endTime = new DateTime();
        interval = new Interval(startTime, endTime);
        logger.info("日志统一打印 ===== {}.{}() end =====,响应时间:{}毫秒,响应内容:\n{}", point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), interval.toDurationMillis(), argsToString(response));
        return response;
    }

    private String argsToString(Object object) {
        try {
            return JSON.toJSONString(object);
        } catch (Exception e) {
            logger.error("", e);
        }
        return String.valueOf(object);
    }

    /**
     * 获取业务参数
     *
     * @param request HttpServletRequest
     * @param param   String
     * @return String
     * @throws Exception 异常
     */
    private String getParam(HttpServletRequest request, String param) throws Exception {
        String[] reqParam = request.getParameterValues(param);
        return (reqParam == null || reqParam.length < 1 ? null : reqParam[0]);
    }

}
