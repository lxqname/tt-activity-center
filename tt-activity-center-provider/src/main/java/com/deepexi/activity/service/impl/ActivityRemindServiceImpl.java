package com.deepexi.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.activity.domain.dto.ActivityRemindDto;
import com.deepexi.activity.domain.eo.ActivityRemind;
import com.deepexi.activity.extension.AppRuntimeEnv;
import com.deepexi.activity.mapper.ActivityRemindMapper;
import com.deepexi.activity.service.ActivityRemindService;
import com.deepexi.activity.service.MemberService;
import com.deepexi.activity.service.MessageService;
import com.deepexi.activity.utils.BaseFieldUtil;
import com.deepexi.activity.utils.DingDingBot;
import com.deepexi.common.enums.ResultEnum;
import com.deepexi.common.enums.YesNoEnum;
import com.deepexi.user.service.AccountService;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.extension.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 活动提醒服务实现
 *
 * @author 蝈蝈
 */
@Component
@Service(version = "${demo.service.version}")
public class ActivityRemindServiceImpl implements ActivityRemindService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityRemindServiceImpl.class);

    @Resource
    private AppRuntimeEnv appRuntimeEnv;

    @Resource
    private ActivityRemindMapper activityRemindMapper;

    @Resource
    private MemberService memberService;

    @Resource
    private MessageService messageService;

    @Reference(version = "${demo.service.version}", check = false)
    private AccountService accountService;

    @Override
    public Boolean create(String activityId) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }

        // 查询会员信息
        String loginMemberId = memberService.getLoginMemberId();

        // 校验是否已存在记录
        int count = activityRemindMapper.count(appRuntimeEnv.getTenantId(), activityId, loginMemberId);
        if (count > 0) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "已设置提醒，请勿重复操作"));
        }

        ActivityRemind activityRemind = new ActivityRemind();
        activityRemind.setMemberId(loginMemberId);
        activityRemind.setActivityId(activityId);
        activityRemind.setSendSuccess(YesNoEnum.NO.getState());
        int insertResult = activityRemindMapper.insert(activityRemind);
        if (insertResult != 1) {
            logger.error("ActivityRemindServiceImpl.create：插入数据操作失败，insert方法返回值为[]", insertResult);
            throw new ApplicationException(ResultEnum.DATABASE_ERROR);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean cancel(String activityId) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        // 查询会员信息
        String loginMemberId = memberService.getLoginMemberId();

        int result = activityRemindMapper.deleteByMemberIdAndActivityId(loginMemberId, activityId, BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
        return result == 1;
    }

    @Override
    public List<String> filterActivityIdListByLogin(List<String> activityIdList) {
        if (CollectionUtil.isEmpty(activityIdList)) {
            return null;
        }

        // 查询会员信息
        String loginMemberId = memberService.getLoginMemberId();

        return activityRemindMapper.selectActivityIdList(appRuntimeEnv.getTenantId(), activityIdList, loginMemberId);
    }

    @Override
    public Integer remind() {
        int effectRows = 0;
        // 查询需要发送消息的活动
        List<ActivityRemindDto> remindList = activityRemindMapper.findRemindActivity();
        if (CollectionUtil.isEmpty(remindList)) {
            // 没有需要提醒的活动
            return effectRows;
        }
        for (ActivityRemindDto dto : remindList) {
            appRuntimeEnv.setTenantId(dto.getTenantCode());
            // 各个互不影响
            try {
                // 发送消息
                messageService.remindActivityStart(dto.getMemberId(), dto.getActivityId(), dto.getActivityName(), dto.getActivityType(), dto.getActivityStartTime());
                // 更新活动提醒表状态
                ActivityRemind activityRemind = new ActivityRemind();
                activityRemind.setId(dto.getId());
                activityRemind.setSendSuccess(YesNoEnum.YES.getState());
                int updateResult = activityRemindMapper.updateById(activityRemind);
                if (updateResult != 1) {
                    DingDingBot.sendMsg("ActivityRemindServiceImpl.remind：活动提醒表更新失败，主键为" + activityRemind.getId());
                }
                effectRows = effectRows + 1;
            } catch (Exception e) {
                logger.error("ActivityRemindServiceImpl.remind：活动提醒异常，错误信息：", e);
            }
        }
        return effectRows;
    }
}