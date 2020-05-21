package com.deepexi.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.activity.domain.eo.ActivityChannel;
import com.deepexi.activity.extension.AppRuntimeEnv;
import com.deepexi.activity.mapper.ActivityChannelMapper;
import com.deepexi.activity.service.ActivityChannelService;
import com.deepexi.activity.utils.BaseFieldUtil;
import com.deepexi.business.service.ChannelService;
import com.deepexi.common.enums.ResultEnum;
import com.deepexi.user.service.AccountService;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.extension.ApplicationException;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * 活动渠道关系服务实现
 *
 * @author 蝈蝈
 */
@Component
@Service(version = "${demo.service.version}")
public class ActivityChannelServiceImpl implements ActivityChannelService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityChannelServiceImpl.class);

    @Resource
    private AppRuntimeEnv appRuntimeEnv;

    @Resource
    private ActivityChannelMapper activityChannelMapper;

    @Reference(version = "${demo.service.version}", check = false)
    private AccountService accountService;

    @Reference(version = "${demo.service.version}", check = false)
    private ChannelService channelService;


    @Override
    public void create(String activityId, String channelId) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        if (StringUtils.isBlank(channelId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，渠道ID为null"));
        }
        ActivityChannel activityChannel = new ActivityChannel();
        activityChannel.setActivityId(activityId);
        activityChannel.setChannelId(channelId);
        int insertResult = activityChannelMapper.insert(activityChannel);
        if (insertResult != 1) {
            logger.error("ActivityChannelServiceImpl.create：插入数据操作失败，insert方法返回值为[]", insertResult);
            throw new ApplicationException(ResultEnum.DATABASE_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(String activityId, List<String> channelIdList) {
        if (CollectionUtil.isEmpty(channelIdList)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，渠道ID列表为null"));
        }
        for (String channelId : channelIdList) {
            this.create(activityId, channelId);
        }
    }

    @Override
    public void deleteByActivityId(String activityId) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID数据为null"));
        }
        activityChannelMapper.deleteByActivityId(activityId, BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
    }

    @Override
    public List<String> findChannelIdList(String activityId) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        return activityChannelMapper.selectChannelIdList(appRuntimeEnv.getTenantId(), activityId);
    }

    @Override
    public List findChannelList(String activityId) {
        List<String> channelIdList = this.findChannelIdList(activityId);
        if (CollectionUtil.isEmpty(channelIdList)) {
            return Lists.newArrayList();
        }
        // 根据ID列表查询渠道信息
        return channelService.listChannelPopupVoByIds(channelIdList);
    }
}