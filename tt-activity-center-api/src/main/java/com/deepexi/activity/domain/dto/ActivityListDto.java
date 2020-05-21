package com.deepexi.activity.domain.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 活动列表接口
 *
 * @author 蝈蝈
 * @since 2019年09月25日 20:26
 */
public class ActivityListDto implements Serializable {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 创建人
     */
    private String createdBy;

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
     * 话题图片URL
     */
    private String topicImageUrl;

    /**
     * 活动描述
     */
    private String description;

    /**
     * 活动状态（0-草稿、1-待审核、2-待生效、3-进行中、4-已失效、5-已驳回、6-商户待审核、7-商户审核驳回）
     */
    private Integer status;

    /**
     * 创建平台（1-运营平台、2-商户平台）
     */
    private Integer applicationType;

    /**
     * 奖品列表
     */
    private List<ActivityAwardDto> awardList;

    /**
     * 活动对象类型（0-全部、1-会员分组、2-关注公众号、3-会员标签）
     */
    private Integer targetType;


    /**
     * 审核状态
     */
    private Integer auditStatus;
    /**
     * 创建企业
     */
    private String createEnterprise;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public String getCreateEnterprise() {
        return createEnterprise;
    }

    public void setCreateEnterprise(String createEnterprise) {
        this.createEnterprise = createEnterprise;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }
}
