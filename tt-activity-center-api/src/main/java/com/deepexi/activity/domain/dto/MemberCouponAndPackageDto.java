package com.deepexi.activity.domain.dto;

import java.io.Serializable;

/**
 * 会员的优惠券/组合包DTO
 *
 * @author 蝈蝈
 * @since 2019年10月16日 11:12
 */
public class MemberCouponAndPackageDto implements Serializable {

    /**
     * ac_member_coupon_relation表ID列表，逗号分隔
     */
    private String idListStr;

    /**
     * 类型（1-优惠券、2-优惠券组合包）
     */
    private Integer type;

    /**
     * 类型ID（优惠券ID/优惠券组合包ID）
     */
    private String typeId;

    /**
     * 类型详情
     */
    private CouponAndPackageDto typeDetail;

    /**
     * 活动优惠券关系ID
     */
    private String activityCouponRelationId;

    /**
     * 活动ID
     */
    private String activityId;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 核销状态（-1-预定、0-待领取、1-未使用/未核销、2-使用中、3-已使用/已核销、4-已过期、5-冻结中、6-退款失效）
     */
    private Integer verificationStatus;

    public String getIdListStr() {
        return idListStr;
    }

    public void setIdListStr(String idListStr) {
        this.idListStr = idListStr;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public CouponAndPackageDto getTypeDetail() {
        return typeDetail;
    }

    public void setTypeDetail(CouponAndPackageDto typeDetail) {
        this.typeDetail = typeDetail;
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

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Integer getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(Integer verificationStatus) {
        this.verificationStatus = verificationStatus;
    }
}
