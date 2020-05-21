package com.deepexi.activity.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员优惠券会员端详情DTO
 *
 * @author 蝈蝈
 * @since 2019年10月23日 11:38
 */
public class MemberCouponH5DetailDto implements Serializable {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 优惠券ID
     */
    private String couponId;

    /**
     * 优惠券唯一编码
     */
    private String couponUniqueCode;

    /**
     * 优惠券组合包ID
     */
    private String couponPackageId;

    /**
     * 活动ID
     */
    private String activityId;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单奖品ID
     */
    private String orderAwardId;

    /**
     * 核销状态（-1-预定、0-待领取、1-未使用/未核销、2-使用中、3-已使用/已核销、4-已过期、5-冻结中、6-退款失效）
     */
    private Integer verificationStatus;

    //------------------------------------------------------------------------

    /**
     * 优惠券信息
     */
    private Object coupon;

    /**
     * 优惠券组合包编码
     */
    private String couponPackageCode;

    /**
     * 使用开始时间-已进行计算处理
     */
    private Date useStartTime;

    /**
     * 使用结束时间-已进行计算处理
     */
    private Date useEndTime;

    /**
     * 活动类型（1-领券活动、2-助力活动、3-发券活动、4-新会员活动,5-活动集）
     */
    private Integer activityType;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 活动对象类型（0-全部、1-会员分组、2-关注公众号、3-会员标签）
     */
    private Integer targetType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponUniqueCode() {
        return couponUniqueCode;
    }

    public void setCouponUniqueCode(String couponUniqueCode) {
        this.couponUniqueCode = couponUniqueCode;
    }

    public String getCouponPackageId() {
        return couponPackageId;
    }

    public void setCouponPackageId(String couponPackageId) {
        this.couponPackageId = couponPackageId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderAwardId() {
        return orderAwardId;
    }

    public void setOrderAwardId(String orderAwardId) {
        this.orderAwardId = orderAwardId;
    }

    public Integer getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(Integer verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public Object getCoupon() {
        return coupon;
    }

    public void setCoupon(Object coupon) {
        this.coupon = coupon;
    }

    public String getCouponPackageCode() {
        return couponPackageCode;
    }

    public void setCouponPackageCode(String couponPackageCode) {
        this.couponPackageCode = couponPackageCode;
    }

    public Date getUseStartTime() {
        return useStartTime;
    }

    public void setUseStartTime(Date useStartTime) {
        this.useStartTime = useStartTime;
    }

    public Date getUseEndTime() {
        return useEndTime;
    }

    public void setUseEndTime(Date useEndTime) {
        this.useEndTime = useEndTime;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }
}
