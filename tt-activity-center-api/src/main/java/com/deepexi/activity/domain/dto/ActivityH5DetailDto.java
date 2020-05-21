package com.deepexi.activity.domain.dto;

import com.deepexi.activity.domain.eo.Activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 活动详情会员端DTO
 *
 * @author 蝈蝈
 * @since 2019年10月12日 18:54
 */
public class ActivityH5DetailDto implements Serializable {

    /**
     * 主键ID
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
     * 活动开始时间
     */
    private Date startTime;

    /**
     * 活动结束时间
     */
    private Date endTime;

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
     * 活动状态（0-草稿、1-待审核、2-待生效、3-进行中、4-已失效、5-已驳回）
     */
    private Integer status;

    /**
     * 系统当前时间
     */
    private Date currentTime;

    /**
     * 提醒我（0-未设置、1-已设置）
     */
    private Integer remindMe;

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
    private List<String> targetIdList;

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
     * @param activity 数据
     */
    public void copy(Activity activity) {
        this.id = activity.getId();
        this.type = activity.getType();
        this.name = activity.getName();
        this.startTime = activity.getStartTime();
        this.endTime = activity.getEndTime();
        this.validTime = activity.getValidTime();
        this.boostAwardIssueType = activity.getBoostAwardIssueType();
        this.thumbnailUrl = activity.getThumbnailUrl();
        this.imageUrl = activity.getImageUrl();
        this.boostImageUrl = activity.getBoostImageUrl();
        this.inviteImageUrl = activity.getInviteImageUrl();
        this.promotionImageUrl = activity.getPromotionImageUrl();
        this.description = activity.getDescription();
        this.boostDescription = activity.getBoostDescription();
        this.status = activity.getStatus();
        this.currentTime = new Date();
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    public Integer getRemindMe() {
        return remindMe;
    }

    public void setRemindMe(Integer remindMe) {
        this.remindMe = remindMe;
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

    public List<String> getTargetIdList() {
        return targetIdList;
    }

    public void setTargetIdList(List<String> targetIdList) {
        this.targetIdList = targetIdList;
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
