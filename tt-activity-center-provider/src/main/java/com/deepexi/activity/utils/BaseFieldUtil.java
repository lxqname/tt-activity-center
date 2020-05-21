package com.deepexi.activity.utils;

import cn.hutool.core.util.IdUtil;
import com.deepexi.common.domain.eo.SuperEntity;
import com.deepexi.user.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 基础字段工具类
 *
 * @author 蝈蝈
 * @since 2019年07月02日 14:19
 */
public class BaseFieldUtil {

    private static final Logger logger = LoggerFactory.getLogger(BaseFieldUtil.class);

    /**
     * 填充基础字段
     *
     * @param eo             实体类
     * @param accountService 账号服务
     * @param tenantCode     租户
     * @param token          Token
     * @param operateTime    操作时间
     * @param <T>            继承SuperEntity的子类
     */
    public static <T extends SuperEntity> void fill(T eo, AccountService accountService, String tenantCode, String token, Date operateTime) {
        String loginAccountId = getLoginAccountId(accountService, token);
        eo.setId(IdUtil.simpleUUID());
        eo.setTenantCode(tenantCode);
        eo.setCreatedAt(operateTime);
        eo.setCreatedBy(loginAccountId);
        eo.setUpdatedAt(operateTime);
        eo.setUpdatedBy(loginAccountId);
    }

    /**
     * 获取登录账号ID
     *
     * @param accountService 账号服务
     * @param token          Token
     * @return String
     */
    public static String getLoginAccountId(AccountService accountService, String token) {
        String loginAccountId = "";
        if (StringUtils.isNotBlank(token)) {
            try {
                loginAccountId = accountService.getLoginAccountIdByToken(token);
            } catch (Exception e) {
                logger.error("BaseFieldUtil.getLoginAccountId：获取登录账号ID失败，原因如下：", e);
            }
        }
        return loginAccountId;
    }
}
