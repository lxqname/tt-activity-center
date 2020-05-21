package com.deepexi.activity.domain.dto;

import com.deepexi.activity.domain.eo.Activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 活动详情DTO
 *
 * @author 蝈蝈
 * @since 2019年09月26日 20:56
 */
public class ActivityDetailDto implements Serializable {

    /**
     * 活动ID
     */
    private String id;

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
     * 预告时间-天数
     */
    private BigDecimal trailDay;

    /**
     * 预告时间-小时数
     */
    private BigDecimal trailHour;

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
     * 渠道推广-渠道信息列表
     */
    private List channelList;

    /**
     * 活动状态（0-草稿、1-待审核、2-待生效、3-进行中、4-已失效、5-已驳回、6-商户待审核、7-商户审核驳回）
     */
    private Integer status;

    /**
     * 奖品列表
     */
    private List<ActivityAwardDto> awardList;

    /**
     * 活动对象类型（0-全部、1-会员分组、2-关注公众号、3-会员标签）
     */
    private Integer targetType;

    /**
     * 活动对象列表
     */
    private List<IdNameDto> targetList;

    /**
     * 发起者条件
     */
    private ActivityConditionDto participateCondition;

    /**
     * 助力者条件
     */
    private ActivityConditionDto helperCondition;

    /**
     * 复制字段
     *
     * @param activity 活动数据
     */
    public void copy(Activity activity) {
        this.id = activity.getId();
        this.type = activity.getType();
        this.name = activity.getName();
        this.startTimeType = activity.getStartTimeType();
        this.startTime = activity.getStartTime();
        this.realStartTime = activity.getRealStartTime();
        this.endTime = activity.getEndTime();
        this.realEndTime = activity.getRealEndTime();
        BigDecimal[] divideAndRemainder = activity.getTrailTime().divideAndRemainder(new BigDecimal("24"));
        this.trailDay = divideAndRemainder[0];
        this.trailHour = divideAndRemainder[1];
        this.validTime = activity.getValidTime();
        this.boostAwardIssueType = activity.getBoostAwardIssueType();
        this.thumbnailUrl = activity.getThumbnailUrl();
        this.imageUrl = activity.getImageUrl();
        this.boostImageUrl = activity.getBoostImageUrl();
        this.inviteImageUrl = activity.getInviteImageUrl();
        this.promotionImageUrl = activity.getPromotionImageUrl();
        this.topicImageUrl = activity.getTopicImageUrl();
        this.description = activity.getDescription();
        this.boostDescription = activity.getBoostDescription();
        this.platformPromotion = activity.getPlatformPromotion();
        this.channelPromotion = activity.getChannelPromotion();
        this.status = activity.getStatus();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public BigDecimal getTrailDay() {
        return trailDay;
    }

    public void setTrailDay(BigDecimal trailDay) {
        this.trailDay = trailDay;
    }

    public BigDecimal getTrailHour() {
        return trailHour;
    }

    public void setTrailHour(BigDecimal trailHour) {
        this.trailHour = trailHour;
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

    public List getChannelList() {
        return channelList;
    }

    public void setChannelList(List channelList) {
        this.channelList = channelList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ActivityAwardDto> getAwardList() {
        return awardList;
    }

    public void setAwardList(List<ActivityAwardDto> awardList) {
        this.awardList = awardList;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public List<IdNameDto> getTargetList() {
        return targetList;
    }

    public void setTargetList(List<IdNameDto> targetList) {
        this.targetList = targetList;
    }

    public ActivityConditionDto getParticipateCondition() {
        return participateCondition;
    }

    public void setParticipateCondition(ActivityConditionDto participateCondition) {
        this.participateCondition = participateCondition;
    }

    public ActivityConditionDto getHelperCondition() {
        return helperCondition;
    }

    public void setHelperCondition(ActivityConditionDto helperCondition) {
        this.helperCondition = helperCondition;
    }
}
