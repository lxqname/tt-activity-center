/**
 * MetaObjectHandlerConfig  2019/3/27
 *
 * Copyright (c) 2018, DEEPEXI Inc. All rights reserved.
 * DEEPEXI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.deepexi.activity.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.deepexi.activity.extension.AppRuntimeEnv;
import com.deepexi.user.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 元对象处理配置
 * mybatis-plus公共字段自动填充，https://baomidou.oschina.io/mybatis-plus-doc/#/auto-fill
 *
 * @author admin
 */
@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String CREATED_AT = "createdAt";
    private static final String TENANT_CODE = "tenantCode";
    private static final String UPDATED_AT = "updatedAt";
    private static final String CREATED_BY = "createdBy";
    private static final String UPDATED_BY = "updatedBy";

    @Resource
    private AppRuntimeEnv appRuntimeEnv;

    @Reference(version = "${demo.service.version}", check = false)
    private AccountService accountService;

    /**
     * 新增方法实体填充
     *
     * @param metaObject 元数据对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName(TENANT_CODE, appRuntimeEnv.getTenantId(), metaObject);
        setFieldValByName(CREATED_AT, new Date(), metaObject);
        String token = appRuntimeEnv.getToken();
        if (StringUtils.isNotBlank(token)) {
            try {
                setFieldValByName(CREATED_BY, accountService.getLoginAccountIdByToken(token), metaObject);
            } catch (Exception e) {
                logger.error("MetaObjectHandlerConfig.insertFill：获取登录账号ID失败，原因如下：", e);
            }
        }
    }

    /**
     * 更新方法实体填充
     *
     * @param metaObject 元数据对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName(UPDATED_AT, new Date(), metaObject);
        String token = appRuntimeEnv.getToken();
        if (StringUtils.isNotBlank(token)) {
            try {
                setFieldValByName(UPDATED_BY, accountService.getLoginAccountIdByToken(token), metaObject);
            } catch (Exception e) {
                logger.error("MetaObjectHandlerConfig.updateFill：获取登录账号ID失败，原因如下：", e);
            }
        }
    }
}