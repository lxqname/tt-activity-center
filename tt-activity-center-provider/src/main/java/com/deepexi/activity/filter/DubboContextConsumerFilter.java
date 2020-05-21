/**
 * DubboContextConsumerFilter  2019/3/27
 *
 * Copyright (c) 2018, DEEPEXI Inc. All rights reserved.
 * DEEPEXI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.deepexi.activity.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.config.spring.ServiceBean;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.deepexi.activity.extension.AppRuntimeEnv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * program: deepexi-activity-center
 * <p>
 * description: 隐式传参
 *
 * @author shaowin
 * <p>
 * created on : 2018-12-13 15:48
 * <p>
 * 问题：
 * <p>
 * 特殊字段不能作为参数key: https://blog.csdn.net/qq_33101675/article/details/73441389
 */
@Activate(group = Constants.CONSUMER)
public class DubboContextConsumerFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(DubboContextConsumerFilter.class);

    private final static String TOKEN_KEY = "userToken";
    private final static String TENANT_ID_KEY = "tenantId";

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            AppRuntimeEnv appRuntimeEnv = (AppRuntimeEnv) ServiceBean.getSpringContext().getBean("appRuntimeEnv");
            Map<String, String> context = new HashMap<>(2);
            context.put(TOKEN_KEY, appRuntimeEnv.getToken());
            context.put(TENANT_ID_KEY, appRuntimeEnv.getTenantId());
            logger.info("rpc filter传参：{}", context);
            RpcContext.getContext().setAttachments(context);
        } catch (Exception e) {
            logger.error("消费端filter统一传参异常:", e);
        }
        return invoker.invoke(invocation);
    }
}
