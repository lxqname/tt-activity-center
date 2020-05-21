package com.deepexi.activity.domain.dto;

import com.deepexi.activity.domain.eo.MemberActivity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员活动详情DTO
 *
 * @author 蝈蝈
 * @since 2019年10月15日 18:51
 */
public class MemberActivityDto implements Serializable {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 活动ID
     */
    private String activityId;

    /**
     * 活动类型
     */
    private Integer activityType;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 活动结束时间
     */
    private Date activityEndTime;

    /**
     * 活动有效时间
     */
    private BigDecimal activityValidTime;

    /**
     * 活动缩略图URL
     */
    private String activityThumbnailUrl;

    /**
     * 活动状态（0-草稿、1-待审核、2-待生效、3-进行中、4-已失效、5-已驳回）
     */
    private Integer activityStatus;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 会员手机号
     */
    private String memberMobile;

    /**
     * 助力人数
     */
    private Integer boostNum;

    /**
     * 状态（1-成功、2-进行中、3-失败）
     */
    private Integer status;

    /**
     * 完成时间
     */
    private Date finishTime;

    /**
     * 分享者渠道ID
     */
    private String shareChannelId;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 系统当前时间
     */
    private Date currentTime;

    /**
     * 获取最近奖励所缺少的助力人数
     */
    private Integer missBoostNum;

    /**
     * 最近奖励ID（优惠券ID/优惠券组合包ID）
     */
    private String awardId;

    /**
     * 最近奖励名称（优惠券名称/优惠券组合包名称）
     */
    private String awardName;

    /**
     * 是否自己发起活动
     */
    private Boolean myself;

    /**
     * 复制字段
     *
     * @param memberActivity 会员活动
     */
    public void copy(MemberActivity memberActivity) {
        this.id = memberActivity.getId();
        this.activityId = memberActivity.getActivityId();
        this.activityType = memberActivity.getActivityType();
        this.activityValidTime = memberActivity.getActivityValidTime();
        this.memberId = memberActivity.getMemberId();
        this.boostNum = memberActivity.getBoostNum();
        this.status = memberActivity.getStatus();
        this.finishTime = memberActivity.getFinishTime();
        this.shareChannelId = memberActivity.getShareChannelId();
        this.createdAt = memberActivity.getCreatedAt();
        this.currentTime = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
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

    public Date getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(Date activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public BigDecimal getActivityValidTime() {
        return activityValidTime;
    }

    public void setActivityValidTime(BigDecimal activityValidTime) {
        this.activityValidTime = activityValidTime;
    }

    public String getActivityThumbnailUrl() {
        return activityThumbnailUrl;
    }

    public void setActivityThumbnailUrl(String activityThumbnailUrl) {
        this.activityThumbnailUrl = activityThumbnailUrl;
    }

    public Integer getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(Integer activityStatus) {
        this.activityStatus = activityStatus;
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

    public Integer getBoostNum() {
        return boostNum;
    }

    public void setBoostNum(Integer boostNum) {
        this.boostNum = boostNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getShareChannelId() {
        return shareChannelId;
    }

    public void setShareChannelId(String shareChannelId) {
        this.shareChannelId = shareChannelId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    public Integer getMissBoostNum() {
        return missBoostNum;
    }

    public void setMissBoostNum(Integer missBoostNum) {
        this.missBoostNum = missBoostNum;
    }

    public String getAwardId() {
        return awardId;
    }

    public void setAwardId(String awardId) {
        this.awardId = awardId;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public Boolean getMyself() {
        return myself;
    }

    public void setMyself(Boolean myself) {
        this.myself = myself;
    }
}
