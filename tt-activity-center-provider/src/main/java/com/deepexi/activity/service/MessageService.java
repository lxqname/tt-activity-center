package com.deepexi.activity.service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 消息服务
 *
 * @author 蝈蝈
 * @since 2019年10月25日 10:36
 */
public interface MessageService {

    /**
     * 优惠券即将过期提醒
     *
     * @param memberId               会员ID
     * @param memberCouponRelationId 会员优惠券关系ID
     * @param receiveTime            领取时间
     * @param couponName             优惠券名称
     * @param couponFaceValue        优惠券面值
     * @param couponExpireTime       优惠券过期时间
     */
    void couponExpireSoon(String memberId, String memberCouponRelationId, Date receiveTime, String couponName, BigDecimal couponFaceValue, Date couponExpireTime);

    /**
     * 助力奖励通知
     *
     * @param memberId      发起者会员ID
     * @param activityId    活动ID
     */
    void issueAwards(String memberId, String activityId);

    /**
     * 活动开始提醒
     *
     * @param memberId          会员ID
     * @param activityId        活动ID
     * @param activityName      活动名称
     * @param activityType      活动类型
     * @param activityStartTime 活动开始时间
     */
    void remindActivityStart(String memberId, String activityId, String activityName, Integer activityType, Date activityStartTime);
}
