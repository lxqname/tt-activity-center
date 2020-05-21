package com.deepexi.activity.domain.dto;

import com.deepexi.activity.domain.eo.MemberCouponRelation;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员优惠券会员端DTO
 *
 * @author 蝈蝈
 * @since 2019年10月22日 16:18
 */
public class MemberCouponH5Dto implements Serializable {

    /**
     * 会员优惠券关系ID
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
     * 优惠券信息
     */
    private Object coupon;

    /**
     * 优惠券使用时间类型（0-不限制、1-指定时间、2-自领取后规定天数有效）
     */
    private Integer couponUseTimeFlag;

    /**
     * 优惠券唯一编码
     */
    private String couponUniqueCode;

    /**
     * 优惠券组合包ID
     */
    private String couponPackageId;

    /**
     * 核销状态（-1-预定、0-待领取、1-未使用/未核销、2-使用中、3-已使用/已核销、4-已过期、5-冻结中、6-退款失效）
     */
    private Integer verificationStatus;

    /**
     * 使用开始时间-已进行计算处理
     */
    private Date useStartTime;

    /**
     * 使用结束时间-已进行计算处理
     */
    private Date useEndTime;

    /**
     * 即将过期（0-否，1-是）
     */
    private Integer expireSoon;

    /**
     * 复制字段
     *
     * @param memberCouponRelation 会员优惠券关系
     */
    public void copy(MemberCouponRelation memberCouponRelation) {
        this.id = memberCouponRelation.getId();
        this.memberId = memberCouponRelation.getMemberId();
        this.couponId = memberCouponRelation.getCouponId();
        this.couponUseTimeFlag = memberCouponRelation.getCouponUseTimeFlag();
        this.couponUniqueCode = memberCouponRelation.getCouponUniqueCode();
        this.couponPackageId = memberCouponRelation.getCouponPackageId();
        this.verificationStatus = memberCouponRelation.getVerificationStatus();
    }

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

    public Object getCoupon() {
        return coupon;
    }

    public void setCoupon(Object coupon) {
        this.coupon = coupon;
    }

    public Integer getCouponUseTimeFlag() {
        return couponUseTimeFlag;
    }

    public void setCouponUseTimeFlag(Integer couponUseTimeFlag) {
        this.couponUseTimeFlag = couponUseTimeFlag;
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

    public Integer getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(Integer verificationStatus) {
        this.verificationStatus = verificationStatus;
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

    public Integer getExpireSoon() {
        return expireSoon;
    }

    public void setExpireSoon(Integer expireSoon) {
        this.expireSoon = expireSoon;
    }
}
