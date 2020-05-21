package com.deepexi.activity.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 活动审核记录DTO
 *
 * @author 蝈蝈
 */
public class ActivityAuditHistoryDto implements Serializable {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 活动ID
     */
    private String activityId;

    /**
     * 审核状态（0-已驳回、1-已通过）
     */
    private Integer status;

    /**
     * 审核备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 创建人
     */
    private String createdBy;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
}

