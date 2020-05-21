package com.deepexi.activity.domain.eo;


import com.baomidou.mybatisplus.annotation.TableName;
import com.deepexi.common.domain.eo.SuperEntity;

import java.io.Serializable;

/**
 * 活动审核记录
 *
 * @author 蝈蝈
 */
@TableName("ac_activity_audit_history")
public class ActivityAuditHistory extends SuperEntity implements Serializable {

    /**
     * 活动ID
     */
    private String activityId;

    /**
     * 审核状态（0-运营平台已驳回、1-运营平台已通过、2-商户平台已驳回、3-商户平台已通过）
     */
    private Integer status;

    /**
     * 审核备注
     */
    private String remark;

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
}

