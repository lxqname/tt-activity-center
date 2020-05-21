package com.deepexi.activity.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 活动提醒DTO
 *
 * @author 蝈蝈
 * @since 2019年11月01日 11:09
 */
public class ActivityRemindDto implements Serializable {

    /**
     * 多租户标识
     */
    private String tenantCode;

    /**
     * 活动提醒主键ID
     */
    private String id;

    /**
     * 活动ID
     */
    private String activityId;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 是否发送（0-未发送、1-已发送）
     */
    private Integer sendSuccess;

    /**
     * 备注
     */
    private String remark;

    /**
     * 活动类型（1-领券活动、2-助力活动、3-发券活动、4-新会员活动,5-活动集）
     */
    private Integer activityType;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 活动开始时间
     */
    private Date activityStartTime;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getSendSuccess() {
        return sendSuccess;
    }

    public void setSendSuccess(Integer sendSuccess) {
        this.sendSuccess = sendSuccess;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Date getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityStartTime(Date activityStartTime) {
        this.activityStartTime = activityStartTime;
    }
}
