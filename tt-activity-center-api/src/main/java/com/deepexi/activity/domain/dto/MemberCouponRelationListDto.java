package com.deepexi.activity.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员优惠券关系列表DTO
 *
 * @author 蝈蝈
 * @since 2019年10月19日 11:00
 */
public class MemberCouponRelationListDto implements Serializable {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 会员手机号
     */
    private String memberMobile;

    /**
     * 优惠券ID
     */
    private String couponId;

    /**
     * 优惠券名称
     */
    private String couponName;

    /**
     * 优惠券面值
     */
    private BigDecimal couponFaceValue;

    /**
     * 优惠券使用条件（核销条件）-（0-无门槛、1-满额、2-自定义）
     */
    private Integer couponUseLimitFlag;

    /**
     * 优惠券自定义核销条件说明
     */
    private String couponUseDivRemark;

    /**
     * 优惠券满额金额
     */
    private BigDecimal couponFullAmount;

    /**
     * 优惠券组合包ID
     */
    private String couponPackageId;

    /**
     * 活动优惠券关系ID
     */
    private String activityCouponRelationId;

    /**
     * 奖励标题
     */
    private String activityCouponRelationTitle;

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
     * 核销人员账号名称
     */
    private String verificationPersonAccountName;

    /**
     * 核销门店ID
     */
    private String verificationStoreId;

    /**
     * 核销门店名称
     */
    private String verificationStoreName;

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

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public BigDecimal getCouponFaceValue() {
        return couponFaceValue;
    }

    public void setCouponFaceValue(BigDecimal couponFaceValue) {
        this.couponFaceValue = couponFaceValue;
    }

    public Integer getCouponUseLimitFlag() {
        return couponUseLimitFlag;
    }

    public void setCouponUseLimitFlag(Integer couponUseLimitFlag) {
        this.couponUseLimitFlag = couponUseLimitFlag;
    }

    public String getCouponUseDivRemark() {
        return couponUseDivRemark;
    }

    public void setCouponUseDivRemark(String couponUseDivRemark) {
        this.couponUseDivRemark = couponUseDivRemark;
    }

    public BigDecimal getCouponFullAmount() {
        return couponFullAmount;
    }

    public void setCouponFullAmount(BigDecimal couponFullAmount) {
        this.couponFullAmount = couponFullAmount;
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

    public String getActivityCouponRelationTitle() {
        return activityCouponRelationTitle;
    }

    public void setActivityCouponRelationTitle(String activityCouponRelationTitle) {
        this.activityCouponRelationTitle = activityCouponRelationTitle;
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

    public String getVerificationPersonAccountName() {
        return verificationPersonAccountName;
    }

    public void setVerificationPersonAccountName(String verificationPersonAccountName) {
        this.verificationPersonAccountName = verificationPersonAccountName;
    }

    public String getVerificationStoreId() {
        return verificationStoreId;
    }

    public void setVerificationStoreId(String verificationStoreId) {
        this.verificationStoreId = verificationStoreId;
    }

    public String getVerificationStoreName() {
        return verificationStoreName;
    }

    public void setVerificationStoreName(String verificationStoreName) {
        this.verificationStoreName = verificationStoreName;
    }
}
