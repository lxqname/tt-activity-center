package com.deepexi.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.deepexi.activity.domain.eo.ActivityCouponRelation;
import com.deepexi.activity.enums.ActivityTypeEnum;
import com.deepexi.activity.extension.AppRuntimeEnv;
import com.deepexi.activity.service.ActivityCouponRelationService;
import com.deepexi.activity.service.MessageService;
import com.deepexi.activity.utils.DingDingBot;
import com.deepexi.member.api.WechatMemberInfoService;
import com.deepexi.member.domain.vo.WechatMemberInfoVO;
import com.deepexi.util.CollectionUtil;
import com.deepexi.wechat.domain.dto.TemplateMessageCommonDTO;
import com.deepexi.wechat.service.WechatTemplateMessageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 消息服务实现
 *
 * @author 蝈蝈
 * @since 2019年10月25日 10:37
 */
@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Resource
    private AppRuntimeEnv appRuntimeEnv;

    private static DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private ActivityCouponRelationService activityCouponRelationService;

    @Reference(version = "${demo.service.version}", check = false)
    private WechatMemberInfoService wechatMemberInfoService;

    @Reference(version = "${demo.service.version}", check = false)
    private WechatTemplateMessageService wechatTemplateMessageService;

    @Override
    public void couponExpireSoon(String memberId, String memberCouponRelationId, Date receiveTime, String couponName, BigDecimal couponFaceValue, Date couponExpireTime) {
        TemplateMessageCommonDTO dto = new TemplateMessageCommonDTO();
        // 查询会员发送消息的公众号（按关注及分流设置的顺序）
        WechatMemberInfoVO wechatMemberInfoVO = wechatMemberInfoService.getInfoVOWechatNumberIdBypass(memberId);
        if (null == wechatMemberInfoVO) {
            logger.info("MessageServiceImpl.couponExpireSoon：会员[{}]查询不到关注公众号信息，无法发送模板消息", memberId);
            return;
        }
        dto.setOpenId(wechatMemberInfoVO.getOpenId());
        dto.setWeChatAccount(wechatMemberInfoVO.getWechatNumberId());
        dto.setUrl(this.getUnusedCouponDetailUrl(wechatMemberInfoVO.getAppId(), memberCouponRelationId));
        dto.setTemplateId("OPENTM206917815");
        Map<String, String> sendMap = new HashMap<>(6);
        sendMap.put("first", "您有一张" + couponFaceValue + "的优惠券还有3天就过期啦~");
        sendMap.put("keyword1", simpleDateFormat.format(receiveTime));
        sendMap.put("keyword2", couponName);
        sendMap.put("keyword3", simpleDateFormat.format(couponExpireTime));
        sendMap.put("keyword4", "3");
        sendMap.put("remark", "查看券详情。");
        dto.setSendMap(sendMap);
        wechatTemplateMessageService.templateMessageCommonSend(dto);
    }

    @Override
    public void issueAwards(String memberId, String activityId) {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        TemplateMessageCommonDTO dto = new TemplateMessageCommonDTO();
        // 查询会员发送消息的公众号（按关注及分流设置的顺序）
        WechatMemberInfoVO wechatMemberInfoVO = wechatMemberInfoService.getInfoVOWechatNumberIdBypass(memberId);
        if (null == wechatMemberInfoVO) {
            logger.info("MessageServiceImpl.issueAwards：会员[{}]查询不到关注公众号信息，无法发送模板消息", memberId);
            return;
        }
        dto.setOpenId(wechatMemberInfoVO.getOpenId());
        dto.setWeChatAccount(wechatMemberInfoVO.getWechatNumberId());
        dto.setUrl(this.getMyUrl(wechatMemberInfoVO.getAppId()));
        dto.setTemplateId("OPENTM206854010");
        Map<String, String> sendMap = new HashMap<>(6);
        sendMap.put("first", "太给力了，您已获得活动奖励！");
        // 查询已获得奖品
        List<ActivityCouponRelation> allCompleteRelation = activityCouponRelationService.findAllCompleteRelation(memberId, activityId);
        if (CollectionUtil.isEmpty(allCompleteRelation)) {
            logger.error("MessageServiceImpl.issueAwards：查询已获得奖品数据为null，会员[{}]，活动[{}]", memberId, activityId);
            return;
        }
        ActivityCouponRelation activityCouponRelation = allCompleteRelation.get(allCompleteRelation.size() - 1);
        String keyword1 = activityCouponRelationService.queryTypeName(activityCouponRelation.getType(), activityCouponRelation.getTypeId());
        if (allCompleteRelation.size() > 1) {
            keyword1 = keyword1 + "等" + allCompleteRelation.size() + "种优惠券";
        }
        sendMap.put("keyword1", keyword1);
        sendMap.put("keyword2", simpleDateFormat.format(new Date()));
        sendMap.put("remark", "奖励已发放到您的账户，请尽快使用喔~");
        dto.setSendMap(sendMap);
        wechatTemplateMessageService.templateMessageCommonSend(dto);
    }

    @Override
    public void remindActivityStart(String memberId, String activityId, String activityName, Integer activityType, Date activityStartTime) {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        TemplateMessageCommonDTO dto = new TemplateMessageCommonDTO();
        // 查询会员发送消息的公众号（按关注及分流设置的顺序）
        WechatMemberInfoVO weChatMemberInfo = wechatMemberInfoService.getInfoVOWechatNumberIdBypass(memberId);
        if (null == weChatMemberInfo) {
            logger.info("MessageServiceImpl.remindActivityStart：会员[{}]查询不到关注公众号信息，无法发送模板消息", memberId);
            return;
        }
        dto.setOpenId(weChatMemberInfo.getOpenId());
        dto.setWeChatAccount(weChatMemberInfo.getWechatNumberId());
        if (Objects.equals(ActivityTypeEnum.RECEIVE_COUPON_ACTIVITY.getState(), activityType)) {
            dto.setUrl(this.getReceiveCouponActivityDetailUrl(weChatMemberInfo.getAppId(), activityId));
        } else if (Objects.equals(ActivityTypeEnum.BOOST_ACTIVITY.getState(), activityType)) {
            dto.setUrl(this.getBoostActivityDetailUrl(weChatMemberInfo.getAppId(), null, activityId, null));
        } else {
            DingDingBot.sendMsg("MessageServiceImpl.remindActivityStart：活动类型异常，无法定位跳转链接，类型为" + activityType + "，活动ID为" + activityId);
        }
        dto.setTemplateId("OPENTM206804349");
        Map<String, String> sendMap = new HashMap<>(6);
        sendMap.put("first", "您关注的活动将在10分钟后开始。");
        sendMap.put("keyword1", "--");
        sendMap.put("keyword2", activityName);
        sendMap.put("keyword3", "公众号【Tt派糖】");
        sendMap.put("keyword4", simpleDateFormat.format(activityStartTime));
        dto.setSendMap(sendMap);
        wechatTemplateMessageService.templateMessageCommonSend(dto);
    }

    /**
     * 获得会员端-我的 URL链接
     *
     * @return String
     */
    private String getMyUrl(String appId) {
        return "/woyou-member-mobile/index.html#/my?tenantId=" + appRuntimeEnv.getTenantId() + "&appId=" + appId;
    }

    /**
     * 获得我的-未使用优惠券-优惠券详情URL链接
     *
     * @param memberCouponRelationId 会员优惠券关系ID
     * @return String
     */
    private String getUnusedCouponDetailUrl(String appId, String memberCouponRelationId) {
        return "/woyou-member-mobile/index.html#/my/coupon/detail?id=" + memberCouponRelationId + "&tenantId=" + appRuntimeEnv.getTenantId() + "&appId=" + appId;
    }

    /**
     * 获得发起者-助力活动详情页URL链接
     *
     * @param appId            AppId
     * @param memberActivityId 发起者会员活动ID
     * @param activityId       活动ID
     * @param channelId        渠道ID
     * @return String
     */
    private String getBoostActivityDetailUrl(String appId, String memberActivityId, String activityId, String channelId) {
        // 发起者-助力活动详情URL
        StringBuilder response = new StringBuilder();
        response.append("/woyou-member-mobile/index.html#/activity/detail?activityId=");
        response.append(activityId);
        response.append("&tenantId=");
        response.append(appRuntimeEnv.getTenantId());
        response.append("&appId=");
        response.append(appId);
        if (StringUtils.isNotBlank(memberActivityId)) {
            response.append("&memberActivityId=");
            response.append(memberActivityId);
        }
        if (StringUtils.isNotBlank(channelId)) {
            response.append("&channelId=");
            response.append(channelId);
        }
        return response.toString();
    }

    /**
     * 获得领券活动详情页URL链接
     *
     * @param appId      AppId
     * @param activityId 活动ID
     * @return String
     */
    private String getReceiveCouponActivityDetailUrl(String appId, String activityId) {
        return "/woyou-member-mobile/index.html#/activity/coupon-detail?id=" + activityId + "&tenantId=" + appRuntimeEnv.getTenantId() + "&appId=" + appId;
    }
}
