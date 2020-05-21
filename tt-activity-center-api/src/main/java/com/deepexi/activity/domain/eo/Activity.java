package com.deepexi.activity.domain.eo;


import com.baomidou.mybatisplus.annotation.TableName;
import com.deepexi.activity.domain.dto.ActivitySaveDto;
import com.deepexi.activity.enums.ChannelPromotionTypeEnum;
import com.deepexi.activity.enums.StartTimeTypeEnum;
import com.deepexi.common.domain.eo.SuperEntity;
import com.deepexi.common.enums.YesNoEnum;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * 活动
 *
 * @author 蝈蝈
 */
@TableName("ac_activity")
public class Activity extends SuperEntity implements Serializable {

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
     * 话题图片URL
     */
    private String topicImageUrl;

    /**
     * 活动描述
     */
    private String description;

    /**
     * 助力说明
     */
    private String boostDescription;

    /**
     * 平台推广（0-不允许推广、1-允许推广）
     */
    private Integer platformPromotion;

    /**
     * 渠道推广（0-不允许推广、1-允许全部渠道推广、2-允许部分渠道推广）
     */
    private Integer channelPromotion;

    /**
     * 活动状态（0-草稿、1-待审核、2-待生效、3-进行中、4-已失效、5-已驳回、6-商户待审核、7-商户审核驳回）
     */
    private Integer status;

    /**
     * 创建平台（1-运营平台、2-商户平台）
     */
    private Integer applicationType;

    /**
     * 字段复制
     *
     * @param saveDto 保存数据
     */
    public void copy(ActivitySaveDto saveDto) {
        this.type = saveDto.getType();
        this.name = saveDto.getName();
        this.startTimeType = Objects.equals(StartTimeTypeEnum.NOW.getState(), saveDto.getStartTimeType()) ? StartTimeTypeEnum.NOW.getState() : StartTimeTypeEnum.ON_TIME.getState();
        this.startTime = saveDto.getStartTime();
        this.endTime = saveDto.getEndTime();
        if (saveDto.getTrailDay() == null) {
            saveDto.setTrailDay(new BigDecimal("0"));
        }
        if (saveDto.getTrailHour() == null) {
            saveDto.setTrailHour(new BigDecimal("0"));
        }
        this.trailTime = saveDto.getTrailDay().multiply(new BigDecimal("24")).add(saveDto.getTrailHour());
        // 确保有效时间只保留小数点后一位,直接删除多余小数位
        this.validTime = saveDto.getValidTime() == null ? BigDecimal.ZERO : saveDto.getValidTime().setScale(1, BigDecimal.ROUND_DOWN);
        this.boostAwardIssueType = saveDto.getBoostAwardIssueType();
        this.thumbnailUrl = saveDto.getThumbnailUrl();
        this.imageUrl = saveDto.getImageUrl();
        this.boostImageUrl = saveDto.getBoostImageUrl();
        this.inviteImageUrl = saveDto.getInviteImageUrl();
        this.promotionImageUrl = saveDto.getPromotionImageUrl();
        this.topicImageUrl = saveDto.getTopicImageUrl();
        this.description = StringUtils.isBlank(saveDto.getDescription()) ? "" : saveDto.getDescription();
        this.boostDescription = StringUtils.isBlank(saveDto.getBoostDescription()) ? "" : saveDto.getBoostDescription();
        this.platformPromotion = saveDto.getPlatformPromotion() == null ? YesNoEnum.NO.getState() : saveDto.getPlatformPromotion();
        this.channelPromotion = saveDto.getChannelPromotion() == null ? ChannelPromotionTypeEnum.NOT_ALLOWED.getState() : saveDto.getChannelPromotion();
        this.status = saveDto.getStatus();
    }

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

    public String getTopicImageUrl() {
        return topicImageUrl;
    }

    public void setTopicImageUrl(String topicImageUrl) {
        this.topicImageUrl = topicImageUrl;
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

    public Integer getPlatformPromotion() {
        return platformPromotion;
    }

    public void setPlatformPromotion(Integer platformPromotion) {
        this.platformPromotion = platformPromotion;
    }

    public Integer getChannelPromotion() {
        return channelPromotion;
    }

    public void setChannelPromotion(Integer channelPromotion) {
        this.channelPromotion = channelPromotion;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(Integer applicationType) {
        this.applicationType = applicationType;
    }
}

