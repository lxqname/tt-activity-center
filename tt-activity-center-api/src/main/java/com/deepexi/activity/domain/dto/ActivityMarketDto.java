package com.deepexi.activity.domain.dto;

import com.deepexi.activity.domain.eo.ActivityTarget;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 推广市场
 * @author  pyf
 */
public class ActivityMarketDto implements Serializable {

    private  String id;

    /**
     * 类型（1-领券活动、2-助力活动、3-发券活动、4-新会员活动,5-活动集）
     */
    private Integer type;

    /**
     * 名称
     */
    private String name;

    /**
     * 活动开始时间类型（1-立即开始、2-定时开始）
     */
    private Integer startTimeType;

    /**
     * 活动开始时间
     */
    private Date startTime;

    /**
     * 活动真实开始时间
     */
    private Date realStartTime;

    /**
     * 活动结束时间
     */
    private Date endTime;

    /**
     * 活动真实结束时间
     */
    private Date realEndTime;

    /**
     * 预告时间
     */
    private BigDecimal trailTime;

    /**
     * 有效时间
     */
    private BigDecimal validTime;

    /**
     * 助力奖品发放条件类型（1-满足任一阶梯后即发放每一阶梯的奖品、2-满足活动最高阶梯后即发放全部阶梯的奖品、3-活动结束后发放所满足的全部阶梯奖品、4-活动结束后发放所满足的最高阶梯奖品、5-有效期结束后发放所满足的全部阶梯奖品、6-有效期结束后发放所满足的最高阶梯奖品）
     */
    private Integer boostAwardIssueType;

    /**
     * 活动缩略图URL
     */
    private String thumbnailUrl;

    /**
     * 发起者活动封面URL
     */
    private String imageUrl;

    /**
     * 助力者活动封面URL
     */
    private String boostImageUrl;

    /**
     * 会员端邀请海报URL
     */
    private String inviteImageUrl;

    /**
     * 推广平台海报URL
     */
    private String promotionImageUrl;

    /**
     * 活动描述
     */
    private String description;

    /**
     * 助力说明
     */
    private String boostDescription;

    /**
     * 关联优惠券类型(1=单,2=组合包)
     */
    private Integer couponType;


    /**
     * 关联优惠券
     */
    private List<ActivityAwardDto> relatedCouponList;

    /**
     * 优惠券包详情
     */
    private Object couponAndPackageDetail;

    /**
     * 活动对象类型
     */
    private Integer activityTargetType;
    /**
     * 对象列表
     */
    private List<ActivityTarget> activityTargetList;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStartTimeType() {
        return startTimeType;
    }

    public void setStartTimeType(Integer startTimeType) {
        this.startTimeType = startTimeType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getRealStartTime() {
        return realStartTime;
    }

    public void setRealStartTime(Date realStartTime) {
        this.realStartTime = realStartTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(Date realEndTime) {
        this.realEndTime = realEndTime;
    }

    public BigDecimal getTrailTime() {
        return trailTime;
    }

    public void setTrailTime(BigDecimal trailTime) {
        this.trailTime = trailTime;
    }

    public BigDecimal getValidTime() {
        return validTime;
    }

    public void setValidTime(BigDecimal validTime) {
        this.validTime = validTime;
    }

    public Integer getBoostAwardIssueType() {
        return boostAwardIssueType;
    }

    public void setBoostAwardIssueType(Integer boostAwardIssueType) {
        this.boostAwardIssueType = boostAwardIssueType;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBoostImageUrl() {
        return boostImageUrl;
    }

    public void setBoostImageUrl(String boostImageUrl) {
        this.boostImageUrl = boostImageUrl;
    }

    public String getInviteImageUrl() {
        return inviteImageUrl;
    }

    public void setInviteImageUrl(String inviteImageUrl) {
        this.inviteImageUrl = inviteImageUrl;
    }

    public String getPromotionImageUrl() {
        return promotionImageUrl;
    }

    public void setPromotionImageUrl(String promotionImageUrl) {
        this.promotionImageUrl = promotionImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBoostDescription() {
        return boostDescription;
    }

    public void setBoostDescription(String boostDescription) {
        this.boostDescription = boostDescription;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getCouponAndPackageDetail() {
        return couponAndPackageDetail;
    }

    public void setCouponAndPackageDetail(Object couponAndPackageDetail) {
        this.couponAndPackageDetail = couponAndPackageDetail;
    }

    public List getRelatedCouponList() {
        return relatedCouponList;
    }

    public void setRelatedCouponList(List relatedCouponList) {
        this.relatedCouponList = relatedCouponList;
    }

    public List<ActivityTarget> getActivityTargetList() {
        return activityTargetList;
    }

    public void setActivityTargetList(List<ActivityTarget> activityTargetList) {
        this.activityTargetList = activityTargetList;
    }

    public Integer getActivityTargetType() {
        return activityTargetType;
    }

    public void setActivityTargetType(Integer activityTargetType) {
        this.activityTargetType = activityTargetType;
    }
}
