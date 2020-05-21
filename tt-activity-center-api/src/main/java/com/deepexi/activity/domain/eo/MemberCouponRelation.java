package com.deepexi.activity.domain.eo;


import com.baomidou.mybatisplus.annotation.TableName;
import com.deepexi.common.domain.eo.SuperEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员优惠券关系
 *
 * @author 蝈蝈
 */
@TableName("ac_member_coupon_relation")
public class MemberCouponRelation extends SuperEntity implements Serializable {

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 优惠券ID
     */
    private String couponId;

    /**
     * 优惠券使用时间类型（0-不限制、1-指定时间、2-自领取后规定天数有效）
     */
    private Integer couponUseTimeFlag;

    /**
     * 优惠券使用开始时间
     */
    private Date couponUseStartTime;

    /**
     * 优惠券使用结束时间
     */
    private Date couponUseEndTime;

    /**
     * 优惠券有效天数（自领取日起）
     */
    private Integer couponValidDay;

    /**
     * 优惠券唯一编码
     */
    private String couponUniqueCode;

    /**
     * 优惠券组合包ID
     */
    private String couponPackageId;

    /**
     * 活动优惠券关系ID
     */
    private String activityCouponRelationId;

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

    /**
     * 领取方式（0-系统发放、1-线上领取、2-线上购买）
     */
    private Integer receiveType;

    /**
     * 领取时间
     */
    private Date receiveTime;

    /**
     * 核销时间
     */
    private Date verificationTime;

    /**
     * 核销人员账号ID
     */
    private String verificationPersonAccountId;

    /**
     * 核销门店ID
     */
    private String verificationStoreId;

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

    public Integer getCouponUseTimeFlag() {
        return couponUseTimeFlag;
    }

    public void setCouponUseTimeFlag(Integer couponUseTimeFlag) {
        this.couponUseTimeFlag = couponUseTimeFlag;
    }

    public Date getCouponUseStartTime() {
        return couponUseStartTime;
    }

    public void setCouponUseStartTime(Date couponUseStartTime) {
        this.couponUseStartTime = couponUseStartTime;
    }

    public Date getCouponUseEndTime() {
        return couponUseEndTime;
    }

    public void setCouponUseEndTime(Date couponUseEndTime) {
        this.couponUseEndTime = couponUseEndTime;
    }

    public Integer getCouponValidDay() {
        return couponValidDay;
    }

    public void setCouponValidDay(Integer couponValidDay) {
        this.couponValidDay = couponValidDay;
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

    public String getActivityCouponRelationId() {
        return activityCouponRelationId;
    }

    public void setActivityCouponRelationId(String activityCouponRelationId) {
        this.activityCouponRelationId = activityCouponRelationId;
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

    public Integer getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(Integer receiveType) {
        this.receiveType = receiveType;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getVerificationTime() {
        return verificationTime;
    }

    public void setVerificationTime(Date verificationTime) {
        this.verificationTime = verificationTime;
    }

    public String getVerificationPersonAccountId() {
        return verificationPersonAccountId;
    }

    public void setVerificationPersonAccountId(String verificationPersonAccountId) {
        this.verificationPersonAccountId = verificationPersonAccountId;
    }

    public String getVerificationStoreId() {
        return verificationStoreId;
    }

    public void setVerificationStoreId(String verificationStoreId) {
        this.verificationStoreId = verificationStoreId;
    }
}

